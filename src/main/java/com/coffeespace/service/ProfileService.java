package com.coffeespace.service;

import com.coffeespace.entity.Profile;
import com.coffeespace.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository repo;

    public Profile createProfile(Profile profile) {
        log.info("Saving Profile for email: {}", profile.getEmail());
        return repo.save(profile);
    }
}
