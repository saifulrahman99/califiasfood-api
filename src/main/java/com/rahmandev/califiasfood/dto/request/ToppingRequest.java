package com.rahmandev.califiasfood.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToppingRequest {
    private String name;
    private Long price;
}
