package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.MenuImageRequest;
import com.rahmandev.califiasfood.dto.request.MenuImageSingleRequest;
import com.rahmandev.califiasfood.dto.response.MenuImageResponse;
import com.rahmandev.califiasfood.entity.MenuImage;
import com.rahmandev.califiasfood.repository.MenuImageRepository;
import com.rahmandev.califiasfood.service.CloudinaryService;
import com.rahmandev.califiasfood.service.MenuImageService;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuImageServiceImpl implements MenuImageService {
    private static final Logger log = LoggerFactory.getLogger(MenuImageServiceImpl.class);
    private final MenuImageRepository menuImageRepository;
    private final CloudinaryService cloudinaryService;
    private final String folderName = "menu";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<MenuImage> createBulk(MenuImageRequest request) {

        List<MenuImage> images = request.getImages().stream().map(
                file -> {
                    if (!List.of("image/jpeg", "image/png", "image/jpg", "image/svg+xml").contains(file.getContentType())) {
                        throw new ConstraintViolationException(ResponseMessage.INVALID_IMAGE_TYPE, null);
                    }
                    String uniqueFilename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    String url = cloudinaryService.uploadFile(file, folderName);
                    return MenuImage.builder()
                            .name(uniqueFilename)
                            .contentType(file.getContentType())
                            .size(file.getSize())
                            .path(url)
                            .menu(request.getMenu())
                            .build();
                }
        ).toList();
        return menuImageRepository.saveAllAndFlush(images);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        MenuImage image = findById(id);
        cloudinaryService.deleteImage(getPublicId(image.getPath()));
        menuImageRepository.delete(image);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByMenuId(String menuId) {
        List<MenuImage> menuImages = findByMenuId(menuId);
        if (menuImages.size() > 1) {
            menuImages.forEach(image -> {
                cloudinaryService.deleteImage(getPublicId(image.getPath()));
            });
            menuImageRepository.deleteAllInBatch(menuImages);
        } else {
            deleteById(menuImages.get(0).getId());
        }
    }

    @Override
    public List<MenuImage> findByMenuId(String menuId) {
        return menuImageRepository.findByMenuId(menuId);
    }

    @Override
    public MenuImage findById(String id) {
        return menuImageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<MenuImage> update(MenuImageRequest request) {
        deleteByMenuId(request.getMenu().getId());
        return createBulk(request);
    }

    @Override
    public MenuImageResponse createSingleImage(MenuImageSingleRequest request) {
        if (!List.of("image/jpeg", "image/png", "image/jpg", "image/svg+xml").contains(request.getImage().getContentType())) {
            throw new ConstraintViolationException(ResponseMessage.INVALID_IMAGE_TYPE, null);
        }
        String uniqueFilename = System.currentTimeMillis() + "_" + request.getImage().getOriginalFilename();
        String url = cloudinaryService.uploadFile(request.getImage(), folderName);
        MenuImage menuImage = menuImageRepository.saveAndFlush(MenuImage.builder()
                .menu(request.getMenu())
                .name(uniqueFilename)
                .size(request.getImage().getSize())
                .contentType(request.getImage().getContentType())
                .path(url)
                .build());
        return MenuImageResponse.builder()
                .id(menuImage.getId())
                .name(menuImage.getName())
                .url(url)
                .build();
    }

    private static String getPublicId(String path) {
        String[] parts = path.split("/v1/");
        return parts.length > 1 ? parts[1].split("\\?")[0] : "";
    }
}
