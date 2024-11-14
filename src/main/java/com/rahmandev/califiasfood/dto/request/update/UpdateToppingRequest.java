package com.rahmandev.califiasfood.dto.request.update;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateToppingRequest {
    private String id;
    private Long price;
    private String name;
}
