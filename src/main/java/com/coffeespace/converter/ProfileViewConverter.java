package com.coffeespace.converter;

import com.coffeespace.dto.*;
import com.coffeespace.entity.Profile;
import com.coffeespace.entity.ProfileEducation;
import com.coffeespace.entity.ProfileExperience;
import com.coffeespace.entity.ProfileAdditionalInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProfileViewConverter {

    public ProfileViewResponse entityToDto(Profile profile,
                                           ProfileAdditionalInfo addl,
                                           List<String> skills,
                                           List<String> industries,
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
                .age(profile.getAge())
                .city(profile.getCity())
                .profilePicUrl(profile.getProfilePicUrl())
                .goal(addl != null ? addl.getGoal() : null)
                .priorities(addl != null && addl.getPriorities() != null
                        ? List.of(addl.getPriorities().split(",")) : List.of())
                .experience(addl != null ? addl.getExperience() : null)
                .skills(skills)
                .industries(industries)
                .experienceList(experiencesToDto(experiences))
                .educationList(educationToDto(educationList))
                .linkedIn(linkedInFromAddl(addl, experiencesToDto(experiences), educationToDto(educationList), skills))
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
                        .isCurrent(Boolean.TRUE.equals(e.getIsCurrent()))
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

    private LinkedInResponse linkedInFromAddl(ProfileAdditionalInfo addl,
                                              List<LinkedInExperienceDTO> exp,
                                              List<LinkedInEducationDTO> edu,
                                              List<String> skills) {
        if (addl == null) return null;
        return LinkedInResponse.builder()
                .profileUrl(addl.getLinkedinProfileUrl())
                .name(addl.getLinkedinName())
                .summary(addl.getLinkedInSummary())
                .connectionsCount(addl.getLinkedInConnectionsCount())
                .experience(exp)
                .education(edu)
                .skills(skills)
                .build();
    }
}
