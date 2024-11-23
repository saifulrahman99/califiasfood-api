package com.rahmandev.califiasfood.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BIllDetailToppingResponse {
    private String id;
    private String toppingId;
    private String toppingName;
    private Long price;
}
