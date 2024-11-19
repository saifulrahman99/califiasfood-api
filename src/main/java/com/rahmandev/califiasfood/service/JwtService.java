package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.response.JwtClaims;
import com.rahmandev.califiasfood.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String bearerToken);

    JwtClaims getClaimsByToken(String bearerToken);
}
