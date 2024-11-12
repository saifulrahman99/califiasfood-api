package com.rahmandev.califiasfood.dto.request.search;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchMenuRequest {
    private String q;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
}
