package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.constant.MenuStatus;
import com.rahmandev.califiasfood.dto.request.MenuRequest;
import com.rahmandev.califiasfood.dto.request.search.SearchMenuRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateMenuRequest;
import com.rahmandev.califiasfood.dto.response.DiscountResponse;
import com.rahmandev.califiasfood.dto.response.MenuImageResponse;
import com.rahmandev.califiasfood.dto.response.MenuResponse;
import com.rahmandev.califiasfood.entity.Category;
import com.rahmandev.califiasfood.entity.Discount;
import com.rahmandev.califiasfood.entity.Menu;
import com.rahmandev.califiasfood.entity.MenuImage;
import com.rahmandev.califiasfood.repository.MenuRepository;
import com.rahmandev.califiasfood.service.CategoryService;
import com.rahmandev.califiasfood.service.MenuImageService;
import com.rahmandev.califiasfood.service.MenuService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
    private final MenuRepository menuRepository;
    private final MenuImageService menuImageService;
    private final CategoryService categoryService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse create(MenuRequest request) {
        Category category = categoryService.getCategoryById(request.getCategoryId());
        MenuStatus menuStatus = MenuStatus.valueOf(request.getMenuStatus());
        Menu menu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .category(category)
                .menuStatus(menuStatus)
                .toppingIsActive(request.getToppingIsActive())
                .build();
        menu.setDiscount(Discount.builder()
                .isActive(false)
                .build());
        menuRepository.saveAndFlush(menu);

        request.getImages().forEach(file -> {
            if (file.isEmpty())
                throw new ConstraintViolationException("image is required", null);
        });
        List<MenuImage> images = menuImageService.createBulk(request.getImages(), menu);
        menu.setImages(images);

        List<MenuImageResponse> menuImageResponses = images.stream().map(image -> {
            return MenuImageResponse.builder()
                    .name(image.getName())
                    .url(image.getPath())
                    .build();
        }).toList();

        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .toppingIsActive(menu.getToppingIsActive())
                .menuStatus(menu.getMenuStatus().toString())
                .images(menuImageResponses)
                .category(
                        Category.builder()
                                .id(menu.getCategory().getId())
                                .name(menu.getCategory().getName())
                                .build()
                )
                .discount(
                        DiscountResponse.builder()
                                .id(menu.getDiscount().getId())
                                .build()
                )
                .build();
    }

    @Override
    public void delete(String id) {
        Menu menu = getMenuById(id);
        menu.setDeleteAt(new Date());

    }

    @Override
    public MenuResponse update(UpdateMenuRequest request) {
        return null;
    }

    @Override
    public Page<MenuResponse> getAll(SearchMenuRequest request) {
        return null;
    }

    @Override
    public Menu getMenuById(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    @Override
    public MenuResponse getById(String id) {
        return null;
    }
}
