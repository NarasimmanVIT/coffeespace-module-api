package com.coffeespace.dto;

import lombok.Data;

@Data
public class LinkedInExperienceDTO {
    private String title;
    private String company;
    private String startDate;
    private String endDate;
    private String location;
    private Boolean isCurrent;
}
