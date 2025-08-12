package com.coffeespace.service;

import com.coffeespace.converter.RegistrationConverter;
import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.dto.RegisterResponse;
import com.coffeespace.dto.LinkedInResponse;
import com.coffeespace.entity.Profile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationConverter converter;
    private final ProfileService profileService;
    private final ProfileAdditionalInfoService additionalInfoService;
    private final ProfileSkillSetService skillSetService;
    private final ProfileInterestedIndustriesService industriesService;
    private final ProfileExperienceService experienceService;
    private final ProfileEducationService educationService;

    @Transactional
    public RegisterResponse register(RegisterRequest req) {
        log.info("Starting registration for email: {}", req.getEmail());

        // Save Profile
        Profile profile = profileService.createProfile(converter.toProfileEntity(req));
        Long pid = profile.getId();

        // Save Additional Info
        additionalInfoService.saveAdditionalInfo(converter.toAdditionalInfoEntity(pid, req));

        // Save Skills
        skillSetService.saveSkills(converter.toSkillSetEntity(pid, req.getSkills()));

        // Save Industries
        industriesService.saveIndustries(converter.toIndustriesEntity(pid, req.getIndustries()));

        // Save Experience (bulk)
        experienceService.saveAll(converter.toExperienceEntities(pid, req.getLinkedInExperience()));

        // Save Education (bulk)
        educationService.saveAll(converter.toEducationEntities(pid, req.getLinkedInEducation()));

        // Build Response
        LinkedInResponse linkedInResponse = LinkedInResponse.builder()
                .profileUrl(req.getLinkedInProfileUrl())
                .name(req.getLinkedInName())
                .summary(req.getLinkedInSummary())
                .connectionsCount(req.getLinkedInConnectionsCount())
                .experience(req.getLinkedInExperience())
                .education(req.getLinkedInEducation())
                .skills(req.getLinkedInSkills())
                .build();

        log.info("Registration completed for email: {}", req.getEmail());

        return RegisterResponse.builder()
                .userId(pid.toString())
                .firstName(profile.getFirstname())
                .lastName(profile.getLastname())
                .email(profile.getEmail())
                .goal(req.getGoal())
                .priorities(req.getPriorities())
                .experience(req.getExperience())
                .skills(req.getSkills())
                .industries(req.getIndustries())
                .linkedIn(linkedInResponse)
                .build();
    }
}
