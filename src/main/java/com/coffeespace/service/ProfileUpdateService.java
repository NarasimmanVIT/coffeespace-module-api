    package com.coffeespace.service;
    
    import com.coffeespace.converter.ProfileUpdateConverter;
    import com.coffeespace.dto.ProfileUpdateRequest;
    import com.coffeespace.dto.RegisterResponse;
    import com.coffeespace.dto.LinkedInResponse;
    import com.coffeespace.entity.Profile;
    import com.coffeespace.repository.ProfileRepository;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    
    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class ProfileUpdateService {
    
        private final ProfileRepository profileRepository;
        private final ProfileUpdateConverter assembler;
    
        private final ProfileAdditionalInfoService additionalInfoService;
        private final ProfileSkillSetService skillSetService;
        private final ProfileInterestedIndustriesService industriesService;
        private final ProfileExperienceService experienceService;
        private final ProfileEducationService educationService;
    
        @Transactional
        public RegisterResponse updateProfile(String profileIdStr, ProfileUpdateRequest req) {
            log.info("Updating profile for id={}", profileIdStr);
    
            Long profileId = Long.parseLong(profileIdStr);
    
            Profile profile = profileRepository.findById(profileId)
                    .orElseThrow(() -> new IllegalArgumentException("Profile not found for userId " + profileId));

            // Update profile core data
            assembler.updateProfileEntity(profile, req);
            profileRepository.save(profile);
    
            additionalInfoService.update(profileId, req);
            skillSetService.updateSkills(profileId, req.getSkills());
            industriesService.updateIndustries(profileId, req.getIndustries());
            experienceService.replaceAll(profileId, req.getLinkedInExperience());
            educationService.replaceAll(profileId, req.getLinkedInEducation());
    
            LinkedInResponse linkedInResponse = assembler.toLinkedInResponse(req);
    
            log.info("Profile updated successfully for id={}", profileId);
    
            return assembler.toRegisterResponse(req, profile, linkedInResponse);
        }
    
    }
