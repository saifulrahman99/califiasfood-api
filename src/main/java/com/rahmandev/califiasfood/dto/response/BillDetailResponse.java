package com.rahmandev.califiasfood.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetailResponse {
    private String id;
    private String menuId;
    private String menuName;
    private Long price;
    private Integer level;
    private Integer qty;
    private List<BIllDetailToppingResponse> billDetailToppings;
}
