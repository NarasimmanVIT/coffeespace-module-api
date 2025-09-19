package com.coffeespace.dto;

import lombok.Data;

@Data
public class TypingIndicator {
    private String conversationId;
    private Long senderId;
    private Long receiverId;
    private boolean typing;
}
