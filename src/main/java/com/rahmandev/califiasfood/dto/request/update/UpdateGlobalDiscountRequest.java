package com.rahmandev.califiasfood.dto.request.update;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateGlobalDiscountRequest {
    private String id;
    private String name;
    private Long discountAmount;
    private String startDate;
    private String endDate;
    private Boolean isActive;
}
