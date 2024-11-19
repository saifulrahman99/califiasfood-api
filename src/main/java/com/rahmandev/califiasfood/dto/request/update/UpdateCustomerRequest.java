package com.rahmandev.califiasfood.dto.request.update;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCustomerRequest {
    private String id;
    private String name;
    private String phoneNumber;
}
