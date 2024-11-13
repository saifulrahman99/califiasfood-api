package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.entity.Menu;
import com.rahmandev.califiasfood.entity.MenuImage;
import com.rahmandev.califiasfood.repository.MenuImageRepository;
import com.rahmandev.califiasfood.service.CloudinaryService;
import com.rahmandev.califiasfood.service.MenuImageService;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class MenuImageServiceImpl implements MenuImageService {
    private final MenuImageRepository menuImageRepository;
    private final CloudinaryService cloudinaryService;
    private final String folderName = "menu";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<MenuImage> createBulk(List<MultipartFile> files, Menu menu) {

        List<MenuImage> images = files.stream().map(
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
                            .menu(menu)
                            .build();
                }
        ).toList();
        return menuImageRepository.saveAllAndFlush(images);
    }

    @Override
    public void deleteById(String id) {
        MenuImage image = findById(id);
        cloudinaryService.deleteImage(getPublicId(image.getPath()));
        menuImageRepository.delete(image);
    }

    @Override
    public void deleteByMenuId(String menuId) {
        List<MenuImage> menuImages = findByMenuId(menuId);
        menuImages.forEach(image -> {
            cloudinaryService.deleteImage(getPublicId(image.getPath()));
        });
        menuImageRepository.deleteByMenuId(menuId);
    }

    @Override
    public List<MenuImage> findByMenuId(String menuId) {
        return menuImageRepository.findByMenuId(menuId);
    }

    @Override
    public MenuImage findById(String id) {
        return menuImageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    private static String getPublicId(String path) {
        Pattern pattern = Pattern.compile("menu/[^?]+");
        Matcher matcher = pattern.matcher(path);
        return matcher.group();
    }
}
