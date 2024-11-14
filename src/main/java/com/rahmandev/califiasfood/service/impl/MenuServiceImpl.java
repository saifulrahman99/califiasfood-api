package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.constant.MenuStatus;
import com.rahmandev.califiasfood.dto.request.MenuRequest;
import com.rahmandev.califiasfood.dto.request.search.SearchMenuRequest;
import com.rahmandev.califiasfood.dto.request.MenuImageRequest;
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
import com.rahmandev.califiasfood.specification.MenuSpecification;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
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
        List<MenuImage> images = menuImageService.createBulk(
                MenuImageRequest.builder()
                        .image(request.getImages())
                        .menu(menu)
                        .build()
        );
        menu.setImages(images);

        List<MenuImageResponse> menuImageResponses = images.stream().map(image -> {
            return MenuImageResponse.builder()
                    .name(image.getName())
                    .url(image.getPath())
                    .build();
        }).toList();

        return getMenuResponse(menu, menuImageResponses);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        Menu menu = getMenuById(id);
        menu.setDeleteAt(new Date());
        menuImageService.deleteByMenuId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse update(UpdateMenuRequest request) {
        Menu menu = getMenuById(request.getId());

        menu.setName(request.getName());
        menu.setPrice(request.getPrice());
        menu.setToppingIsActive(request.getToppingIsActive());
        menu.setCategory(categoryService.getCategoryById(request.getCategoryId()));
        menu.setDiscount(menu.getDiscount());
        MenuStatus menuStatus = MenuStatus.valueOf(request.getMenuStatus());
        menu.setMenuStatus(menuStatus);

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            List<MenuImage> newImages = menuImageService.update(
                    MenuImageRequest.builder()
                            .menu(menu)
                            .image(request.getImages())
                            .build());
            List<MenuImageResponse> menuImageResponses = newImages.stream().map(image -> {
                return MenuImageResponse.builder()
                        .name(image.getName())
                        .url(image.getPath())
                        .build();
            }).toList();
            return getMenuResponse(menu, menuImageResponses);
        } else {
            List<MenuImageResponse> menuImageResponses = menu.getImages().stream().map(image -> {
                return MenuImageResponse.builder()
                        .name(image.getName())
                        .url(image.getPath())
                        .build();
            }).toList();
            return getMenuResponse(menu, menuImageResponses);
        }
    }

    @Override
    public Page<MenuResponse> getAll(SearchMenuRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        Specification<Menu> specification = MenuSpecification.getSpecification(request.getQ());
        Page<Menu> menus = menuRepository.findAll(specification, pageable);

        List<MenuResponse> menuResponses = menus.getContent().stream()
                .map(
                        this::getMenuResponse
                ).toList();

        return new PageImpl<>(menuResponses, pageable, menus.getTotalElements());
    }

    @Override
    public Menu getMenuById(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    @Override
    public MenuResponse getById(String id) {
        Menu menu = getMenuById(id);
        return getMenuResponse(menu);
    }

    private MenuResponse getMenuResponse(Menu menu) {
        DiscountResponse discountResponse = DiscountResponse.builder()
                .id(menu.getDiscount().getId())
                .discountAmount(menu.getDiscount().getDiscountAmount())
                .startDate(menu.getDiscount().getStartDate())
                .endDate(menu.getDiscount().getEndDate())
                .isActive(menu.getDiscount().getIsActive())
                .MenuName(menu.getName())
                .MenuPrice(menu.getPrice())
                .build();
        List<MenuImageResponse> imageResponses = menu.getImages().stream().map(
                image -> {
                    return MenuImageResponse.builder()
                            .name(image.getName())
                            .url(image.getPath())
                            .build();
                }
        ).toList();

        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .toppingIsActive(menu.getToppingIsActive())
                .menuStatus(menu.getMenuStatus().toString())
                .images(imageResponses)
                .category(menu.getCategory())
                .discount(discountResponse)
                .build();
    }

    private MenuResponse getMenuResponse(Menu menu, List<MenuImageResponse> menuImageResponses) {
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
                                .isActive(menu.getDiscount().getIsActive())
                                .MenuName(menu.getName())
                                .MenuPrice(menu.getPrice())
                                .build()
                )
                .build();
    }
}
