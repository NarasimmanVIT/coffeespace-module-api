package com.coffeespace.controller;

import com.coffeespace.dto.*;
import com.coffeespace.service.MessageService;
import com.coffeespace.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final JwtUtil jwtUtil;

    @GetMapping("/{conversationId}")
    public ApiResponse<PaginatedMessageResponse> getMessages(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String token = authHeader.substring(7);
        jwtUtil.extractUserId(token);

        PaginatedMessageResponse response = messageService.getMessages(conversationId, page, size);

        return ApiResponse.<PaginatedMessageResponse>builder()
                .success(true)
                .statusCode(200)
                .message("Messages fetched successfully")
                .data(messageService.getMessages(conversationId, page, size)) // ✅ now matches
                .build();
    }

    @PostMapping
    public ApiResponse<MessageResponse> sendMessage(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody MessageRequest req) {

        String token = authHeader.substring(7);
        Long senderId = Long.valueOf(jwtUtil.extractUserId(token));

        MessageResponse response = messageService.sendMessage(senderId, req);

        return ApiResponse.<MessageResponse>builder()
                .message("Message sent successfully")
                .statusCode(200)
                .success(true)
                .data(response)
                .build();
    }
}
