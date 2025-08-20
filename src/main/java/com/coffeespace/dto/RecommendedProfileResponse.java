package com.coffeespace.dto;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class RecommendedProfileResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private double score; // computed in SQL
}
