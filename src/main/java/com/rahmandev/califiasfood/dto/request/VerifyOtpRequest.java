package com.rahmandev.califiasfood.dto.request;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyOtpRequest {
    private String userAccountId;
    private Integer otpCode;
}
