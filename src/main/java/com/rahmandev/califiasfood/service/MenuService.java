package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.MenuRequest;
import com.rahmandev.califiasfood.dto.request.search.SearchMenuRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateMenuRequest;
import com.rahmandev.califiasfood.dto.response.MenuResponse;
import com.rahmandev.califiasfood.entity.Menu;
import org.springframework.data.domain.Page;

public interface MenuService {
    MenuResponse create(MenuRequest request);

    void delete(String id);

    MenuResponse update(UpdateMenuRequest request);

    Page<MenuResponse> getAll(SearchMenuRequest request);

    Menu getMenuById(String id);

    MenuResponse getById(String id);

}