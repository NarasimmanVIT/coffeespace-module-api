package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ProfileViewResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;
    private LocalDate dob;
    private Integer age;
    private String city;
    private String profilePicUrl;

    private String goal;
    private List<String> priorities;
    private String experience;
    private List<String> skills;
    private List<String> industries;

    private List<LinkedInExperienceDTO> experienceList;
    private List<LinkedInEducationDTO> educationList;
    private LinkedInResponse linkedIn;
}
