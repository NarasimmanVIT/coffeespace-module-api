package com.coffeespace.service;

import com.coffeespace.converter.ProfileConverter;
import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.entity.Profile;
import com.coffeespace.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConverter profileConverter;


    public Profile saveProfile(RegisterRequest req) {
        log.info("Saving profile for email: {}", req.getEmail());
        Profile entity = profileConverter.modelToEntity(req);
        return profileRepository.save(entity);
    }

    /**
     * Find a profile by contact number and return as a DTO.
     * @param contactNumber phone number to search by
     * @return optional RegisterRequest DTO if found
     */
    public Optional<RegisterRequest> findByContactNumber(String contactNumber) {
        log.debug("Looking for profile with contact number: {}", contactNumber);
        return profileRepository.findByContactNumber(contactNumber)
                .map(profileConverter::entityToModel);
    }

    /**
     * Find a profile by email and return as a DTO.
     * @param email email address to search by
     * @return optional RegisterRequest DTO if found
     */
    public Optional<RegisterRequest> findByEmail(String email) {
        log.debug("Looking for profile with email: {}", email);
        return profileRepository.findByEmail(email)
                .map(profileConverter::entityToModel);
    }

    /**
     * Find a profile entity directly by email (used internally).
     */
    public Optional<Profile> findEntityByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    /**
     * Update an existing profile (e.g., to modify the profilePicUrl).
     */
    public Profile updateProfile(Profile profile) {
        log.info("Updating profile for id={}", profile.getId());
        return profileRepository.save(profile);
    }
}
