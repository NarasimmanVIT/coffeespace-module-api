package com.coffeespace.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private String conversationId; // e.g. "conv_123"
    private Long receiverId;
    private String text;
}
