package com.rahmandev.califiasfood.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuImageRequest {
    private String id;
    private String name;
    private String path;
    private Long size;
    private String contentType;
}
