package com.coffeespace.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PaginatedConversationResponse {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private List<ConversationResponse> profiles;
}
