package com.coffeespace.repository;

import com.coffeespace.entity.ProfileInterestedIndustries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileInterestedIndustriesRepository extends JpaRepository<ProfileInterestedIndustries, Long> {

    // Fetch industries by profileId
    Optional<ProfileInterestedIndustries> findByProfileid(Long profileId);

    // Delete industries by profileId
    void deleteByProfileid(Long profileId);

    // Check if industries exist for profileId
    boolean existsByProfileid(Long profileId);
}
