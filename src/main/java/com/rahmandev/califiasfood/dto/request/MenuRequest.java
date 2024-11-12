package com.rahmandev.califiasfood.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuRequest {
    private String name;
    private Long price;
    private Boolean toppingIsActive;
    private String categoryId;
    private String menuStatus;
    private List<MultipartFile> images;
}
