package com.coffeespace.service;

import com.coffeespace.converter.ProfileEducationConverter;
import com.coffeespace.dto.LinkedInEducationDTO;
import com.coffeespace.entity.ProfileEducation;
import com.coffeespace.repository.ProfileEducationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileEducationService {

    private final ProfileEducationRepository repo;
    private final ProfileEducationConverter converter;

    public void saveAll(Long profileId, List<LinkedInEducationDTO> educationDTOs) {
        List<ProfileEducation> entities = converter.modelToEntities(profileId, educationDTOs);
        if (entities.isEmpty()) {
            log.debug("No education records to save for profileId {}", profileId);
            return;
        }
        log.info("Saving {} education records for profileId {}", entities.size(), profileId);
        repo.saveAll(entities);
    }

    public List<LinkedInEducationDTO> getByProfileId(Long profileId) {
        List<ProfileEducation> entities = repo.findByProfileid(profileId);
        log.debug("Found {} education records for profileId {}", entities.size(), profileId);
        return converter.entityToModels(entities);
    }

    @Transactional
    public void replaceAll(Long profileId, List<LinkedInEducationDTO> edus) {
        log.info("Replacing education records for profileId={}", profileId);
        repo.deleteByProfileid(profileId);
        List<ProfileEducation> entities = converter.modelToEntities(profileId, edus);
        repo.saveAll(entities);
    }
}
