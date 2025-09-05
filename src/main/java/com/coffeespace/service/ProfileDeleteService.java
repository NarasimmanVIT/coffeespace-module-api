package com.coffeespace.service;

import com.coffeespace.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileDeleteService {

    private final ProfileRepository profileRepository;
    private final ProfileAdditionalInfoRepository additionalInfoRepo;
    private final ProfileSkillSetRepository skillSetRepo;
    private final ProfileInterestedIndustriesRepository industriesRepo;
    private final ProfileExperienceRepository experienceRepo;
    private final ProfileEducationRepository educationRepo;

    @Transactional
    public void deleteProfile(String profileIdStr) {
        Long profileId = Long.parseLong(profileIdStr);
        log.info("Deleting profile and all related data for id={}", profileId);

        if (!profileRepository.existsById(profileId)) {
            throw new IllegalArgumentException("Profile not found for id " + profileId);
        }

        // Delete child entities first (to maintain referential integrity)
        additionalInfoRepo.deleteByProfileid(profileId);
        skillSetRepo.deleteByProfileid(profileId);
        industriesRepo.deleteByProfileid(profileId);
        experienceRepo.deleteByProfileid(profileId);
        educationRepo.deleteByProfileid(profileId);

        // Finally delete the profile itself
        profileRepository.deleteById(profileId);

        log.info("Profile and related records deleted successfully for id={}", profileId);
    }
}
