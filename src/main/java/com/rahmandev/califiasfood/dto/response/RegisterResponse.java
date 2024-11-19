package com.rahmandev.califiasfood.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private String userAccountId;
    private String email;
    private List<String> roles;
    private String idCustomer;
}
