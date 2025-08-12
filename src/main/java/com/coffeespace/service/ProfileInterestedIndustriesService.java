package com.coffeespace.service;

import com.coffeespace.entity.ProfileInterestedIndustries;
import com.coffeespace.repository.ProfileInterestedIndustriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileInterestedIndustriesService {
    private final ProfileInterestedIndustriesRepository repo;

    public void saveIndustries(ProfileInterestedIndustries industries) {
        log.info("Saving industries for profileId: {}", industries.getProfileid());
        repo.save(industries);
    }
}
