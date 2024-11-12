package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.entity.Menu;
import com.rahmandev.califiasfood.entity.MenuImage;
import com.rahmandev.califiasfood.repository.MenuImageRepository;
import com.rahmandev.califiasfood.service.CloudinaryService;
import com.rahmandev.califiasfood.service.MenuImageService;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuImageServiceImpl implements MenuImageService {
    private final MenuImageRepository menuImageRepository;
    private final CloudinaryService cloudinaryService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<MenuImage> createBulk(List<MultipartFile> files, Menu menu) {

        List<MenuImage> images = files.stream().map(
                file -> {
                    String folderName = "menu";
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
    public Resource getById(String id) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}
