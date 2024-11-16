package com.rahmandev.califiasfood.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuImageResponse {
    private String id;
    private String name;
    private String url;
}
