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
public class RegisterResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private LocalDate dob;  // or String, but LocalDate is better
    private String city;
    private String goal;
    private List<String> priorities;
    private String experience;
    private List<String> skills;
    private List<String> industries;
    private LinkedInResponse linkedIn;
}
