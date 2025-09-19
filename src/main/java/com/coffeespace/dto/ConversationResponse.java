package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ConversationResponse {
    private String conversationId;
    private Long participantId;
    private String name;
    private String avatar;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
    private int unreadCount;
    private boolean isOnline;
}
