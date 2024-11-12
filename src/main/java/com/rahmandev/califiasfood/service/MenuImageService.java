package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.entity.Menu;
import com.rahmandev.califiasfood.entity.MenuImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MenuImageService {
    List<MenuImage> createBulk(List<MultipartFile> files, Menu menu);

    Resource getById(String id);

    void deleteById(String id);
}
