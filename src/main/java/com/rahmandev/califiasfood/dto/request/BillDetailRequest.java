package com.rahmandev.califiasfood.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailRequest {
    @NotBlank
    private String menuId;
    @NotBlank
    private Integer level;
    @NotNull
    private Integer qty;

    List<BillDetailToppingRequest> toppings;
}
