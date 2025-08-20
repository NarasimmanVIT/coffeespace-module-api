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
    private String city;

    private List<LinkedInExperienceDTO> experience;
    private List<LinkedInEducationDTO> education;
}
