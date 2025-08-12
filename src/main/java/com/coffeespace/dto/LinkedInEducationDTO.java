package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkedInEducationDTO {
    private String institutionName;
    private String degree;
    private String fieldOfStudy;
    private Integer startYear;
    private Integer endYear;
}
