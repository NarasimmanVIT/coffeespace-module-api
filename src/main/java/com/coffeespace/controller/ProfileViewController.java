package com.coffeespace.controller;

import com.coffeespace.dto.ApiResponse;
import com.coffeespace.dto.ProfileViewResponse;
import com.coffeespace.service.ProfileViewService;
import com.coffeespace.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileViewController {

    private final ProfileViewService profileViewService;
    private final JwtUtil jwtUtil;

    /**
     * Fetch profile of currently authenticated user using token.
     * URL: GET /api/profile/me
     */
    @GetMapping("/me")
    public ApiResponse<ProfileViewResponse> getMyProfile(
            @RequestHeader("Authorization") String authHeader) {

        log.info("GET /api/profile/me with token");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);  // should return profileId or phone/email

        ProfileViewResponse data = profileViewService.getProfile(Long.valueOf(userId));

        return ApiResponse.<ProfileViewResponse>builder()
                .message("Profile fetched successfully")
                .statusCode(200)
                .success(true)
                .data(data)
                .build();
    }

    /**
     * Fetch profile by explicit ID (for admin or debug usage).
     * URL: GET /api/profile/{id}
     */
    @GetMapping("/{id}")
    public ApiResponse<ProfileViewResponse> getProfileById(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String authHeader) {

        log.info("GET /api/profile/{} with token", id);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        // Optionally verify if current user has admin role
        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);
        log.debug("Token verified for user: {}", userId);

        ProfileViewResponse data = profileViewService.getProfile(id);

        return ApiResponse.<ProfileViewResponse>builder()
                .message("Profile fetched successfully")
                .statusCode(200)
                .success(true)
                .data(data)
                .build();
    }
}
