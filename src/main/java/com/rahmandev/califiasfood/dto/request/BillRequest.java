package com.rahmandev.califiasfood.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillRequest {
    @NotBlank
    private String customerId;
    @NotBlank
    private String deliveryType;
    @NotNull
    private List<BillDetailRequest> billDetails;
    @NotBlank
    private String paymentType;
}
