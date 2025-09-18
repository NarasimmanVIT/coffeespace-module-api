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
    public ApiResponse<PaginatedResponse<ProfileSummaryResponse>> getReceivedInvites(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String profileId = jwtUtil.extractUserId(authHeader.substring(7));
        PaginatedResponse<ProfileSummaryResponse> response =
                inviteService.getReceivedInvites(Long.parseLong(profileId), page, size);

        return ApiResponse.<PaginatedResponse<ProfileSummaryResponse>>builder()
                .success(true)
                .statusCode(200)
                .message("Received invites fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/sent")
    public ApiResponse<PaginatedResponse<ProfileSummaryResponse>> getSentInvites(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String profileId = jwtUtil.extractUserId(authHeader.substring(7));
        PaginatedResponse<ProfileSummaryResponse> response =
                inviteService.getSentInvites(Long.parseLong(profileId), page, size);

        return ApiResponse.<PaginatedResponse<ProfileSummaryResponse>>builder()
                .success(true)
                .statusCode(200)
                .message("Sent invites fetched successfully")
                .data(response)
                .build();
    }
}
