package com.coffeespace.controller;

import com.coffeespace.dto.ApiResponse;
import com.coffeespace.dto.PaginatedResponse;
import com.coffeespace.dto.ProfileSummaryResponse;
import com.coffeespace.service.InviteService;
import com.coffeespace.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/invites")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;
    private final JwtUtil jwtUtil;

    @GetMapping("/received")
    public ApiResponse<PaginatedResponse<ProfileSummaryResponse>> received(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String profileId = jwtUtil.extractUserId(token);

        log.info("Fetching received invites for profile ID: {}", profileId);

        PaginatedResponse<ProfileSummaryResponse> invites =
                inviteService.getReceivedInvites(Long.parseLong(profileId), page, size);

        return ApiResponse.<PaginatedResponse<ProfileSummaryResponse>>builder()
                .success(true)
                .statusCode(200)
                .message("Received invites retrieved successfully")
                .data(invites)
                .build();
    }

    @GetMapping("/sent")
    public ApiResponse<PaginatedResponse<ProfileSummaryResponse>> sent(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String profileId = jwtUtil.extractUserId(token);

        log.info("Fetching sent invites for profile ID: {}", profileId);

        PaginatedResponse<ProfileSummaryResponse> invites =
                inviteService.getSentInvites(Long.parseLong(profileId), page, size);

        return ApiResponse.<PaginatedResponse<ProfileSummaryResponse>>builder()
                .success(true)
                .statusCode(200)
                .message("Sent invites retrieved successfully")
                .data(invites)
                .build();
    }
}
