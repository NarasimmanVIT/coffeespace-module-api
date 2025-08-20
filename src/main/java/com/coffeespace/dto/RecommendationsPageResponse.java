package com.coffeespace.dto;

import lombok.*;

import java.util.List;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class RecommendationsPageResponse {
    private List<RecommendedProfileResponse> items;
    private int page;       // zero-based
    private int size;       // page size
    private long total;     // total number of candidates considered
    private int totalPages; // derived = ceil(total / size)
}
