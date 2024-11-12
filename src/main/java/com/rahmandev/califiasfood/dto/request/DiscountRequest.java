package com.rahmandev.califiasfood.dto.request;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountRequest {
    private Long discountAmount;
    private Date startDate;
    private Date endDate;
    private Boolean isActive;
    private String menuId;
}
