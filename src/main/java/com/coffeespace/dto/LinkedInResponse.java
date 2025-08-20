package com.coffeespace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkedInResponse {
    private String profileUrl;
    private String name;
    private String summary;
    private Integer connectionsCount;
    private List<LinkedInExperienceDTO> experience;
    private List<LinkedInEducationDTO> education;
    private List<String> skills;
}
