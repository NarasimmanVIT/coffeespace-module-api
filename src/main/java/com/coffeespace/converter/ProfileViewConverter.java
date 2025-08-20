package com.coffeespace.converter;

import com.coffeespace.dto.ProfileViewResponse;
import com.coffeespace.dto.LinkedInEducationDTO;
import com.coffeespace.dto.LinkedInExperienceDTO;
import com.coffeespace.entity.Profile;
import com.coffeespace.entity.ProfileEducation;
import com.coffeespace.entity.ProfileExperience;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProfileViewConverter {

    /**
     * Convert a Profile entity + child entities to a ProfileViewResponse DTO.
     */
    public ProfileViewResponse entityToDto(Profile profile,
                                           List<ProfileExperience> experiences,
                                           List<ProfileEducation> educationList) {
        log.debug("Converting Profile entity to ProfileViewResponse DTO for id: {}", profile.getId());

        return ProfileViewResponse.builder()
                .userId(profile.getId().toString())
                .firstName(profile.getFirstname())
                .lastName(profile.getLastname())
                .contactNumber(profile.getContactNumber())
                .email(profile.getEmail())
                .dob(profile.getDob())
                .city(profile.getCity())
                .experience(experiencesToDto(experiences))
                .education(educationToDto(educationList))
                .build();
    }

    private List<LinkedInExperienceDTO> experiencesToDto(List<ProfileExperience> exps) {
        if (exps == null || exps.isEmpty()) return List.of();
        return exps.stream()
                .map(e -> LinkedInExperienceDTO.builder()
                        .title(e.getRole())
                        .company(e.getCompany())
                        .startDate(e.getStartdate())
                        .endDate(e.getEnddate())
                        .location(e.getLocation())
                        .isCurrent(e.getEnddate() == null) // infer current role
                        .build())
                .collect(Collectors.toList());
    }

    private List<LinkedInEducationDTO> educationToDto(List<ProfileEducation> edus) {
        if (edus == null || edus.isEmpty()) return List.of();
        return edus.stream()
                .map(ed -> LinkedInEducationDTO.builder()
                        .institutionName(ed.getInstitutename())
                        .degree(ed.getDegree())
                        .fieldOfStudy(ed.getDepartment())
                        .startYear(ed.getStartdate() != null ? Integer.valueOf(ed.getStartdate()) : null)
                        .endYear(ed.getEnddate() != null ? Integer.valueOf(ed.getEnddate()) : null)
                        .build())
                .collect(Collectors.toList());
    }
}
