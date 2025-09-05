package com.coffeespace.converter;

import com.coffeespace.dto.LinkedInResponse;
import com.coffeespace.dto.ProfileUpdateRequest;
import com.coffeespace.dto.RegisterResponse;
import com.coffeespace.entity.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProfileUpdateConverter {

    public void updateProfileEntity(Profile p, ProfileUpdateRequest req) {
        log.debug("Mapping ProfileUpdateRequest into existing Profile entity");
        if (req.getFirstName() != null) p.setFirstname(req.getFirstName());
        if (req.getLastName() != null) p.setLastname(req.getLastName());
        if (req.getContactNumber() != null) p.setContactNumber(req.getContactNumber());
        if (req.getEmail() != null) p.setEmail(req.getEmail());
        if (req.getDob() != null) p.setDob(req.getDob());
        if (req.getCity() != null) p.setCity(req.getCity());
    }

    public LinkedInResponse toLinkedInResponse(ProfileUpdateRequest req) {
        return LinkedInResponse.builder()
                .profileUrl(req.getLinkedInProfileUrl())
                .name(req.getLinkedInName())
                .summary(req.getLinkedInSummary())
                .connectionsCount(req.getLinkedInConnectionsCount())
                .experience(req.getLinkedInExperience())
                .education(req.getLinkedInEducation())
                .skills(req.getLinkedInSkills())
                .build();
    }

    public RegisterResponse toRegisterResponse(ProfileUpdateRequest req, Profile profile, LinkedInResponse linkedInResponse) {
        return RegisterResponse.builder()
                .userId(profile.getId().toString())
                .firstName(profile.getFirstname())
                .lastName(profile.getLastname())
                .email(profile.getEmail())
                .contactNumber(profile.getContactNumber())
                .dob(profile.getDob())
                .city(profile.getCity())
                .goal(req.getGoal())
                .priorities(req.getPriorities())
                .experience(req.getExperience())
                .skills(req.getSkills())
                .industries(req.getIndustries())
                .linkedIn(linkedInResponse)
                .build();
    }
}
