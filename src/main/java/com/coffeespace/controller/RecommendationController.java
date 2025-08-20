package com.coffeespace.controller;

import com.coffeespace.dto.ApiResponse;
import com.coffeespace.dto.RecommendationsPageResponse;
import com.coffeespace.service.ProfileRecommendationService;
import com.coffeespace.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final ProfileRecommendationService recommendationService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ApiResponse<RecommendationsPageResponse> recommend(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        // Your JwtUtil should carry the profileId in the "sub" or a custom claim
        String profileIdStr = jwtUtil.extractUserId(token);
        Long profileId = Long.parseLong(profileIdStr);

        RecommendationsPageResponse data = recommendationService.recommendProfiles(profileId, page, size);

        return ApiResponse.<RecommendationsPageResponse>builder()
                .success(true)
                .statusCode(200)
                .message("Recommendations fetched")
                .data(data)
                .build();
    }
}
