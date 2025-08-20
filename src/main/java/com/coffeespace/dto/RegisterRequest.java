package com.coffeespace.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String goal;
    private List<String> priorities;
    private String experience;
    private List<String> skills;
    private List<String> industries;
    private String contactNumber;
    private LocalDate dob;  // or String, but LocalDate is better
    private String city;


    private String linkedInProfileUrl;
    private String linkedInName;
    private String linkedInSummary;
    private Integer linkedInConnectionsCount;
    private List<LinkedInExperienceDTO> linkedInExperience;
    private List<LinkedInEducationDTO> linkedInEducation;
    private List<String> linkedInSkills;
}
