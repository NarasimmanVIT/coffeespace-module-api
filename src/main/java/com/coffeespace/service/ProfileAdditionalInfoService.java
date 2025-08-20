package com.coffeespace.service;

import com.coffeespace.converter.ProfileAdditionalInfoConverter;
import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.entity.ProfileAdditionalInfo;
import com.coffeespace.repository.ProfileAdditionalInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileAdditionalInfoService {

    private final ProfileAdditionalInfoRepository repo;
    private final ProfileAdditionalInfoConverter converter;

    /**
     * Converts DTO to entity and saves profile additional info.
     */
    public void save(Long profileId, RegisterRequest req) {
        ProfileAdditionalInfo info = converter.modelToEntity(profileId, req);
        log.info("Saving additional info for profileId: {}", profileId);
        repo.save(info);
    }

    /**
     * Fetches additional info by profile ID and converts back to DTO.
     */
    public RegisterRequest getByProfileId(Long profileId) {
        ProfileAdditionalInfo info = repo.findByProfileid(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Additional info not found for profileId: " + profileId));
        log.debug("Found additional info for profileId {}", profileId);
        return converter.entityToModel(info);
    }
}
