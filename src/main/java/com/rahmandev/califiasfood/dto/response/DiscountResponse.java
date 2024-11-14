package com.rahmandev.califiasfood.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountResponse {
    private String id;
    private Long discountAmount;
    private Date startDate;
    private Date endDate;
    private Boolean isActive;
    private String MenuName;
    private Long MenuPrice;
}
