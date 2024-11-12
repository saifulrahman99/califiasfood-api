package com.rahmandev.califiasfood.dto.response;

import com.rahmandev.califiasfood.entity.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse {
    private String id;
    private String name;
    private Long price;
    private Boolean toppingIsActive;
    private Category category;
    private String menuStatus;
    private DiscountResponse discount;
    private List<MenuImageResponse> images;
}
