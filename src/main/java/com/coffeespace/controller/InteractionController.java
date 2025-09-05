package com.coffeespace.controller;

import com.coffeespace.dto.ApiResponse;
import com.coffeespace.dto.InteractionRequest;
import com.coffeespace.service.InteractionService;
import com.coffeespace.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/interactions")
@RequiredArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ApiResponse<Void> interact(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody InteractionRequest req) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String profileId = jwtUtil.extractUserId(token);

        log.info("Handling interaction for profile ID: {}", profileId);

        interactionService.handleInteraction(Long.parseLong(profileId), req);

        return ApiResponse.<Void>builder()
                .success(true)
                .statusCode(200)
                .message("Interaction processed successfully")
                .data(null)
                .build();
    }
}
