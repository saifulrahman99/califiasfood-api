package com.rahmandev.califiasfood.dto.request.update;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAddressRequest {
    private String id;
    private String address;
}
