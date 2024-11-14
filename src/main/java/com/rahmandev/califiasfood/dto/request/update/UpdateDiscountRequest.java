package com.rahmandev.califiasfood.dto.request.update;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDiscountRequest {
    private String id;
    private Long discountAmount;
    private String startDate;
    private String endDate;
    private Boolean isActive;
}

