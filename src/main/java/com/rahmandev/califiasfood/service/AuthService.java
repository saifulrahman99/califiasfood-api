package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.RegisterRequest;
import com.rahmandev.califiasfood.dto.request.LoginRequest;
import com.rahmandev.califiasfood.dto.request.VerifyOtpRequest;
import com.rahmandev.califiasfood.dto.response.LoginResponse;
import com.rahmandev.califiasfood.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);

    RegisterResponse registerAdmin(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    void verificationOtpToActiveAccount(VerifyOtpRequest request);

    void sendOtpAgain(String userAccountId);
}
