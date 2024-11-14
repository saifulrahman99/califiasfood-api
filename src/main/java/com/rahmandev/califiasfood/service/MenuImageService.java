package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.MenuImageRequest;
import com.rahmandev.califiasfood.entity.MenuImage;

import java.util.List;

public interface MenuImageService {
    List<MenuImage> createBulk(MenuImageRequest request);

    void deleteById(String id);

    void deleteByMenuId(String id);

    List<MenuImage> findByMenuId(String menuId);

    MenuImage findById(String id);

    List<MenuImage> update(MenuImageRequest request);


}
