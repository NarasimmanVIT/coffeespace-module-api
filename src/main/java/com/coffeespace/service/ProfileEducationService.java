package com.coffeespace.service;

import com.coffeespace.entity.ProfileEducation;
import com.coffeespace.repository.ProfileEducationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileEducationService {
    private final ProfileEducationRepository repo;

    public void saveAll(List<ProfileEducation> educationList) {
        if (educationList == null || educationList.isEmpty()) {
            log.debug("No education records to save");
            return;
        }
        log.info("Saving {} education records for profileId {}", educationList.size(), educationList.get(0).getProfileid());
        repo.saveAll(educationList);
    }
}
