package com.rahmandev.califiasfood.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToppingResponse {
    private String id;
    private Long price;
    private String name;
}
