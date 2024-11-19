package com.rahmandev.califiasfood.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private String id;
    private String name;
    private String phoneNumber;
    private String userAccountId;
    private List<AddressResponse> addresses;
}
