package com.coffeespace.service;

import com.coffeespace.converter.ProfileExperienceConverter;
import com.coffeespace.dto.LinkedInExperienceDTO;
import com.coffeespace.entity.ProfileExperience;
import com.coffeespace.repository.ProfileExperienceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileExperienceService {

    private final ProfileExperienceRepository repository;
    private final ProfileExperienceConverter converter;

    // ===== Save (Model → Entity) =====
    @Transactional
    public void saveAll(Long profileId, List<LinkedInExperienceDTO> experiences) {
        log.info("Saving {} experiences for profileId={}",
                experiences != null ? experiences.size() : 0, profileId);
        List<ProfileExperience> entities = converter.modelToEntities(profileId, experiences);
        repository.saveAll(entities);
    }

    // ===== Fetch (Entity → Model) =====
    @Transactional(readOnly = true)
    public List<LinkedInExperienceDTO> getByProfileId(Long profileId) {
        log.info("Fetching experiences for profileId={}", profileId);
        List<ProfileExperience> entities = repository.findByProfileid(profileId);
        return converter.entityToModels(entities);
    }

    // ===== Optional: Delete experiences before save (if needed for updates) =====
    @Transactional
    public void replaceAll(Long profileId, List<LinkedInExperienceDTO> newExperiences) {
        log.info("Replacing experiences for profileId={}", profileId);
        repository.deleteByProfileid(profileId);
        saveAll(profileId, newExperiences);
    }
}
