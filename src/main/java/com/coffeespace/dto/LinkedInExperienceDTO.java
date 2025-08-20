package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkedInExperienceDTO {
    private String title;
    private String company;
    private String startDate;
    private String endDate;
    private String location;
    private Boolean isCurrent;
}
