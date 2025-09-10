package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageResponse {
    private String messageId;
    private String conversationId;
    private Long senderId;
    private Long receiverId;
    private String text;
    private LocalDateTime sentAt;
    private boolean isRead;
}
