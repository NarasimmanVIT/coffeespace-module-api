package com.coffeespace.repository;

import com.coffeespace.entity.ProfileEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileEducationRepository extends JpaRepository<ProfileEducation, Long> {

    // Fetch all education records for a given profileId
    List<ProfileEducation> findByProfileid(Long profileId);

    // Delete all education records for a given profileId
    void deleteByProfileid(Long profileId);

    // Count how many education records a profile has
    long countByProfileid(Long profileId);
}
