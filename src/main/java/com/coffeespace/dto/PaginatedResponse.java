package com.coffeespace.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class PaginatedResponse<T> {
    private List<T> items;
    private int page;
    private int size;
    private long total;
    private int totalPages;
}
