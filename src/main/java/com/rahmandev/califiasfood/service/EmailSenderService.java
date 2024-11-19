package com.rahmandev.califiasfood.service;

public interface EmailSenderService {
    void sendOtp(String to, Integer otp);
}
