package com.coffeespace.controller;


import com.coffeespace.dto.ApiResponse;
import com.coffeespace.service.ProfileDeleteService;
import com.coffeespace.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileDeleteController {

    private final ProfileDeleteService profileDeleteService;
    private final JwtUtil jwtUtil;

    @DeleteMapping
    public ApiResponse<Void> deleteProfile(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        String profileId = jwtUtil.extractUserId(token);

        profileDeleteService.deleteProfile(profileId);

        return ApiResponse.<Void>builder()
                .success(true)
                .statusCode(200)
                .message("Profile deleted successfully")
                .data(null)
                .build();
    }
}
