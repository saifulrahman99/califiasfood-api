package com.rahmandev.califiasfood.dto.request.update;

import com.rahmandev.califiasfood.dto.request.MenuImageRequest;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMenuRequest {
    private String id;
    private String name;
    private Long price;
    private Boolean toppingIsActive;
    private String categoryId;
    private String menuStatus;
    private List<MenuImageRequest> images;
}
