package com.rahmandev.califiasfood.dto.request.update;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePaymentStatusRequest {
    @NotNull
    private String billId;
    @NotNull
    private String paymentStatus;
}
