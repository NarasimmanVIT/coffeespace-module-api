package com.coffeespace.controller;

import com.coffeespace.dto.ApiResponse;
import com.coffeespace.dto.MessageRequest;
import com.coffeespace.dto.MessageResponse;
import com.coffeespace.dto.PaginatedMessageResponse;
import com.coffeespace.service.MessageService;
import com.coffeespace.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final JwtUtil jwtUtil;

    /**
     * API #13 — Fetch messages (paginated) for conversation and mark unread as read.
     * GET /api/conversations/{conversationId}/messages?page=0&size=10
     */
    @GetMapping("/api/conversations/{conversationId}/messages")
    public ApiResponse<PaginatedMessageResponse> getMessages(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        Long profileId = Long.parseLong(jwtUtil.extractUserId(authHeader.substring(7)));
        log.info("GET messages for conversation={} requester={}", conversationId, profileId);

        PaginatedMessageResponse resp = messageService.getMessages(profileId, conversationId, page, size);

        return ApiResponse.<PaginatedMessageResponse>builder()
                .success(true)
                .statusCode(200)
                .message("Messages fetched successfully")
                .data(resp)
                .build();
    }

    /**
     * API #15 — Send message
     * POST /api/messages
     */
    @PostMapping("/api/messages")
    public ApiResponse<MessageResponse> sendMessage(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody MessageRequest req) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        Long senderId = Long.parseLong(jwtUtil.extractUserId(authHeader.substring(7)));
        log.info("POST send message from={} to={} conv={}", senderId, req.getReceiverId(), req.getConversationId());

        MessageResponse created = messageService.sendMessage(senderId, req);

        return ApiResponse.<MessageResponse>builder()
                .success(true)
                .statusCode(200)
                .message("Message sent successfully")
                .data(created)
                .build();
    }
}
