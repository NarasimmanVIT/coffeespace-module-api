package com.coffeespace.controller;

import com.coffeespace.dto.ApiResponse;
import com.coffeespace.dto.InviteResponseDto;
import com.coffeespace.dto.InviteResponseRequest;
import com.coffeespace.service.InviteResponseService;
import com.coffeespace.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/invite/response")
@RequiredArgsConstructor
public class InviteResponseController {

    private final InviteResponseService inviteResponseService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ApiResponse<InviteResponseDto> respond(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody InviteResponseRequest req) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String profileId = jwtUtil.extractUserId(token);

        log.info("Processing invite response for profile ID: {}, type: {}", profileId, req.getType());

        InviteResponseDto dto = inviteResponseService.handleResponse(Long.parseLong(profileId), req);

        return ApiResponse.<InviteResponseDto>builder()
                .success(true)
                .statusCode(200)
                .message("Invite " + req.getType().toLowerCase() + "ed successfully")
                .data(dto)
                .build();
    }
}
