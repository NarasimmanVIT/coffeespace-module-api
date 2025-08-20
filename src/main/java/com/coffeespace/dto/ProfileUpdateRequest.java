package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ProfileUpdateRequest {
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;
    private LocalDate dob;
    private String city;

    private String goal;
    private List<String> priorities;
    private String experience;
    private List<String> skills;
    private List<String> industries;

    private String linkedInProfileUrl;
    private String linkedInName;
    private String linkedInSummary;
    private Integer linkedInConnectionsCount;
    private List<LinkedInExperienceDTO> linkedInExperience;
    private List<LinkedInEducationDTO> linkedInEducation;
    private List<String> linkedInSkills;
}
