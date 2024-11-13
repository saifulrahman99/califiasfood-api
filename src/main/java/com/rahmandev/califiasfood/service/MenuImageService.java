package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.entity.Menu;
import com.rahmandev.califiasfood.entity.MenuImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MenuImageService {
    List<MenuImage> createBulk(List<MultipartFile> files, Menu menu);

    void deleteById(String id);

    void deleteByMenuId(String id);

    List<MenuImage> findByMenuId(String menuId);
    MenuImage findById(String id);
}
