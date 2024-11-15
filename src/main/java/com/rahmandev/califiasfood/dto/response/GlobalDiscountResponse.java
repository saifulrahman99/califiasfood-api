package com.rahmandev.califiasfood.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GlobalDiscountResponse {
    private String id;
    private String name;
    private Long discountAmount;
    private String startDate;
    private String endDate;
    private Boolean isActive;
}
