package com.coffeespace.service;

import com.coffeespace.entity.Otp;
import com.coffeespace.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;

    public Otp saveOtp(Otp otp) {
        return otpRepository.save(otp);
    }

    public Optional<Otp> getLatestOtpByPhone(String phoneNumber) {
        return otpRepository.findTopByPhoneNumberOrderByExpiryTimeDesc(phoneNumber);
    }

}
