package com.rahmandev.califiasfood.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GlobalDiscountRequest {
    private String name;
    private Long discountAmount;
    private String startDate;
    private String endDate;
    private Boolean isActive;
}
