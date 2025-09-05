package com.coffeespace.service;

import com.coffeespace.converter.ProfileViewConverter;
import com.coffeespace.dto.ProfileViewResponse;
import com.coffeespace.entity.Profile;
import com.coffeespace.entity.ProfileEducation;
import com.coffeespace.entity.ProfileExperience;
import com.coffeespace.repository.ProfileEducationRepository;
import com.coffeespace.repository.ProfileExperienceRepository;
import com.coffeespace.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileViewService {

    private final ProfileRepository profileRepository;
    private final ProfileExperienceRepository experienceRepository;
    private final ProfileEducationRepository educationRepository;
    private final ProfileViewConverter converter;

    public ProfileViewResponse getProfile(Long profileId) {
        log.info("Fetching profile view for profileId={}", profileId);

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found with id " + profileId));
        // replace with custom NotFoundException if you have one

        List<ProfileExperience> experiences = experienceRepository.findByProfileid(profileId);
        log.debug("Found {} experiences for profileId={}", experiences.size(), profileId);

        List<ProfileEducation> educationList = educationRepository.findByProfileid(profileId);
        log.debug("Found {} education records for profileId={}", educationList.size(), profileId);

        return converter.entityToDto(profile, experiences, educationList);
    }
}
