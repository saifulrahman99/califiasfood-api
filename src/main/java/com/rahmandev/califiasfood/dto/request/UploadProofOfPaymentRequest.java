package com.rahmandev.califiasfood.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadProofOfPaymentRequest {
    @NotNull
    private String billId;
    @NotNull
    private MultipartFile image;
}
