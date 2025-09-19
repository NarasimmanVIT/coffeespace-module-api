package com.coffeespace.controller;

import com.coffeespace.dto.ApiResponse;
import com.coffeespace.dto.PaginatedResponse;
import com.coffeespace.dto.ProfileSummaryResponse;
import com.coffeespace.service.ConnectionService;
import com.coffeespace.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/connections")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ApiResponse<PaginatedResponse<ProfileSummaryResponse>> getConnections(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);

        PaginatedResponse<ProfileSummaryResponse> data =
                connectionService.getConnections(Long.parseLong(userId), page, size);

        return ApiResponse.<PaginatedResponse<ProfileSummaryResponse>>builder()
                .success(true)
                .statusCode(200)
                .message("Connections fetched successfully")
                .data(data)
                .build();
    }
}
