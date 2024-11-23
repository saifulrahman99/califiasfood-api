package com.rahmandev.califiasfood.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillResponse {
    private String id;
    private String billDate;
    private String deliveryType;
    private String customerId;
    private String customerName;
    private PaymentResponse payment;
    private List<BillDetailResponse> billDetails;
}
