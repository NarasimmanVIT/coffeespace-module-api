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

        String token = authHeader.substring(7);
        String profileId = jwtUtil.extractUserId(token);

        PaginatedConversationResponse response = conversationService.getConversations(Long.valueOf(profileId), page, size);

        return ApiResponse.<PaginatedConversationResponse>builder()
                .message("Conversations fetched successfully")
                .statusCode(200)
                .success(true)
                .data(response)
                .build();
    }
}
