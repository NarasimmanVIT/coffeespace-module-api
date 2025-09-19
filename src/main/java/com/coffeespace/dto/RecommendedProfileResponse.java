package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecommendedProfileResponse {

    private Long profileId;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private Integer age;
    private String goal;
    private String experience;
    private List<String> skills;
    private List<String> industries;
    private String role;
    private Double score;
}
