package com.coffeespace.service;

import com.coffeespace.entity.ProfileExperience;
import com.coffeespace.repository.ProfileExperienceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileExperienceService {
    private final ProfileExperienceRepository repo;

    public void saveAll(List<ProfileExperience> experiences) {
        if (experiences == null || experiences.isEmpty()) {
            log.debug("No experiences to save");
            return;
        }
        log.info("Saving {} experiences for profileId {}", experiences.size(), experiences.get(0).getProfileid());
        repo.saveAll(experiences);
    }
}
