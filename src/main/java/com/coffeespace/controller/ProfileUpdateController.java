package com.coffeespace.controller;

import com.coffeespace.dto.ApiResponse;
import com.coffeespace.dto.ProfileUpdateRequest;
import com.coffeespace.dto.RegisterResponse;
import com.coffeespace.service.ProfileUpdateService;
import com.coffeespace.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileUpdateController {

    private final ProfileUpdateService profileUpdateService;
    private final JwtUtil jwtUtil;

    @PutMapping
    public ApiResponse<RegisterResponse> updateProfile(
            @RequestBody ProfileUpdateRequest req,
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String profileId = jwtUtil.extractUserId(token);

        RegisterResponse response = profileUpdateService.updateProfile(profileId, req);

        return ApiResponse.<RegisterResponse>builder()
                .success(true)
                .statusCode(200)
                .message("Profile updated successfully")
                .data(response)
                .build();
    }
}
