package com.coffeespace.service;

import com.coffeespace.converter.ProfileViewConverter;
import com.coffeespace.dto.ProfileViewResponse;
import com.coffeespace.entity.Profile;
import com.coffeespace.entity.ProfileEducation;
import com.coffeespace.entity.ProfileExperience;
import com.coffeespace.entity.ProfileAdditionalInfo;
import com.coffeespace.repository.ProfileEducationRepository;
import com.coffeespace.repository.ProfileExperienceRepository;
import com.coffeespace.repository.ProfileRepository;
import com.coffeespace.repository.ProfileAdditionalInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileViewService {

    private final ProfileRepository profileRepository;
    private final ProfileAdditionalInfoRepository additionalInfoRepository;
    private final ProfileExperienceRepository experienceRepository;
    private final ProfileEducationRepository educationRepository;
    private final ProfileSkillSetService skillSetService;
    private final ProfileInterestedIndustriesService industriesService;
    private final ProfileViewConverter converter;

    public ProfileViewResponse getProfile(Long profileId) {
        log.info("Fetching profile view for profileId={}", profileId);

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found with id " + profileId));

        ProfileAdditionalInfo addl = additionalInfoRepository.findByProfileid(profileId).orElse(null);
        List<String> skills = skillSetService.getSkills(profileId);
        List<String> industries = industriesService.getIndustries(profileId);
        List<ProfileExperience> experiences = experienceRepository.findByProfileid(profileId);
        List<ProfileEducation> educationList = educationRepository.findByProfileid(profileId);

        return converter.entityToDto(profile, addl, skills, industries, experiences, educationList);
    }
}
