package com.rahmandev.califiasfood.dto.request;

import com.rahmandev.califiasfood.entity.Menu;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuImageRequest {
    private Menu menu;
    private List<MultipartFile> image;
}
