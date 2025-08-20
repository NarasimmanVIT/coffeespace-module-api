package com.coffeespace.service;

import com.coffeespace.dto.LinkedInResponse;
import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.dto.RegisterResponse;
import com.coffeespace.entity.Profile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final ProfileService profileService;
    private final ProfileAdditionalInfoService additionalInfoService;
    private final ProfileSkillSetService skillSetService;
    private final ProfileInterestedIndustriesService industriesService;
    private final ProfileExperienceService experienceService;
    private final ProfileEducationService educationService;

    @Transactional
    public RegisterResponse register(RegisterRequest req) {
        log.info("Starting registration for email: {}", req.getEmail());


        Profile profile = profileService.saveProfile(req);
        Long pid = profile.getId();

        additionalInfoService.save(pid, req);
        skillSetService.saveSkills(pid, req.getSkills());
        industriesService.saveIndustries(pid, req.getIndustries());
        experienceService.saveAll(pid, req.getLinkedInExperience());
        educationService.saveAll(pid, req.getLinkedInEducation());



        LinkedInResponse linkedInResponse = LinkedInResponse(req);

        log.info("Registration completed for email: {}", req.getEmail());

        return RegisterResponse(req, profile, pid, linkedInResponse);
    }

    private LinkedInResponse LinkedInResponse(RegisterRequest req) {
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

    private RegisterResponse RegisterResponse(RegisterRequest req, Profile profile, Long pid, LinkedInResponse linkedInResponse) {
        return RegisterResponse.builder()
                .userId(pid.toString())
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
