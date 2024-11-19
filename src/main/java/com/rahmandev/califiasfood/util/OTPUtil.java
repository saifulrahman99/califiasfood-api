package com.rahmandev.califiasfood.util;

import java.security.SecureRandom;

public class OTPUtil {
    public static Integer generateOTP() {

        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            otp.append(digit);
        }
        return Integer.parseInt(otp.toString());
    }
}
