package com.rahmandev.califiasfood.dto.request;

import com.rahmandev.califiasfood.entity.Menu;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuImageSingleRequest {
    private Menu menu;
    private MultipartFile image;
}
