package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedMessageResponse {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private List<MessageResponse> messages;
}
