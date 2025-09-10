package com.coffeespace.controller;

import com.coffeespace.dto.ApiResponse;
import com.coffeespace.dto.PaginatedConversationResponse;
import com.coffeespace.service.ConversationService;
import com.coffeespace.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ApiResponse<PaginatedConversationResponse> getConversations(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        Long profileId = Long.parseLong(jwtUtil.extractUserId(token));
        log.info("GET /api/conversations for profileId={}", profileId);

        PaginatedConversationResponse data = conversationService.getConversations(profileId, page, size);

        return ApiResponse.<PaginatedConversationResponse>builder()
                .success(true)
                .statusCode(200)
                .message("Conversations fetched successfully")
                .data(data)
                .build();
    }
}
