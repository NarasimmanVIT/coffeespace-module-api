package com.coffeespace.repository;

import com.coffeespace.entity.ProfileExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileExperienceRepository extends JpaRepository<ProfileExperience, Long> {

    // Fetch all experience records for a given profileId
    List<ProfileExperience> findByProfileid(Long profileId);

    // Delete all experience records for a given profileId
    void deleteByProfileid(Long profileId);

    // Check if experience records exist for a profile
    boolean existsByProfileid(Long profileId);
}
