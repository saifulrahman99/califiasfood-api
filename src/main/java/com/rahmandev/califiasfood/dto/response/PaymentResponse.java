package com.rahmandev.califiasfood.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String id;
    private String paymentType;
    private String paymentStatus;
    private String proofOfPaymentPath;
}
