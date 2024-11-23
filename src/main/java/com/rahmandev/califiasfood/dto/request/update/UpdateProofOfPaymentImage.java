package com.rahmandev.califiasfood.dto.request.update;

import com.rahmandev.califiasfood.entity.Bill;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProofOfPaymentImage {
    @NotNull
    private Bill bill;
    @NotNull
    private MultipartFile image;
}
