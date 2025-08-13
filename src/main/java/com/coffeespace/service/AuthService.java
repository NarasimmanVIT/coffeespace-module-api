package com.coffeespace.service;

import com.coffeespace.constants.ResponseConstants;
import com.coffeespace.dto.*;
import com.coffeespace.entity.Otp;
import com.coffeespace.entity.Profile;
import com.coffeespace.repository.ProfileRepository; // <-- add this import
import com.coffeespace.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final OtpService otpService;
    private final JwtUtil jwtUtil;
    private final ProfileRepository profileRepository; // <-- injected

    public ApiResponse<SendOtpData> sendOtp(SendOtpRequest request) {
        log.info("Sending OTP to phone number: {}", request.getPhoneNumber());

        String staticOtp = "1111";
        int expiryInSeconds = 500;

        Otp otp = Otp.builder()
                .phoneNumber(request.getPhoneNumber())
                .otp(staticOtp)
                .expiryTime(LocalDateTime.now().plusSeconds(expiryInSeconds))
                .build();

        otpService.saveOtp(otp);

        return ApiResponse.<SendOtpData>builder()
                .message(ResponseConstants.OTP_SENT)
                .statusCode(ResponseConstants.SUCCESS_CODE)
                .success(true)
                .data(SendOtpData.builder()
                        .otp(staticOtp)
                        .expiryInSeconds(expiryInSeconds)
                        .build())
                .build();
    }

    public ApiResponse<VerifyOtpData> verifyOtp(VerifyOtpRequest request) {
        log.info("Verifying OTP for phone number: {}", request.getPhoneNumber());

        if (request == null ||
                request.getPhoneNumber() == null || request.getPhoneNumber().isBlank() ||
                request.getOtp() == null || request.getOtp().isBlank()) {
            return buildError(ResponseConstants.INVALID_PHONE, ResponseConstants.BAD_REQUEST_CODE);
        }

        Optional<Otp> latestOtpOpt = otpService.getLatestOtpByPhone(request.getPhoneNumber());

        if (latestOtpOpt.isEmpty()) {
            log.warn("No OTP found for phone: {}", request.getPhoneNumber());
            return buildError(ResponseConstants.INVALID_PHONE, ResponseConstants.BAD_REQUEST_CODE);
        }

        Otp latestOtp = latestOtpOpt.get();

        if (!latestOtp.getOtp().equals(request.getOtp()) ||
                latestOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
            log.warn("Invalid or expired OTP for phone: {}", request.getPhoneNumber());
            return buildError(ResponseConstants.INVALID_OR_EXPIRED_OTP, ResponseConstants.BAD_REQUEST_CODE);
        }

        String token = jwtUtil.generateToken(request.getPhoneNumber());

        // Check if profile exists
        Optional<Profile> profileOpt = profileRepository.findByContactNumber(request.getPhoneNumber());
        String profileId = profileOpt.map(p -> p.getId().toString()).orElse(null);

        log.info("OTP verified. Existing profile ID: {}", profileId);

        return ApiResponse.<VerifyOtpData>builder()
                .message(ResponseConstants.OTP_VERIFIED)
                .statusCode(ResponseConstants.SUCCESS_CODE)
                .success(true)
                .data(VerifyOtpData.builder()
                        .token(token)
                        .profileId(profileId)
                        .build())
                .build();
    }

    private ApiResponse<VerifyOtpData> buildError(String msg, int status) {
        return ApiResponse.<VerifyOtpData>builder()
                .message(msg)
                .statusCode(status)
                .success(false)
                .data(null)
                .build();
    }
}
