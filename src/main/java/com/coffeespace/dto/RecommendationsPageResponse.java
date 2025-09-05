package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecommendationsPageResponse {
    private List<RecommendedProfileResponse> profiles;  
    private int page;
    private int size;
    private long total;
    private int totalPages;
}
