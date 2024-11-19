package com.rahmandev.califiasfood.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String address;
}
