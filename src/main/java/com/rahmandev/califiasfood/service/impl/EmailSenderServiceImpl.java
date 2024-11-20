package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(String to, Integer otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("OTP Verification");
        message.setText("Your OTP is: " + otp + "\n\nPlease do not share this code with anyone.\n\nThank you!");
        mailSender.send(message);
    }
}
