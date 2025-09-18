package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {
    private String conversationId;
    private Long senderId;
    private Long receiverId;
    private String text;
}
