package com.coffeespace.repository;

import com.coffeespace.entity.ProfileAdditionalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileAdditionalInfoRepository extends JpaRepository<ProfileAdditionalInfo, Long> {

    // Fetch additional info by profileId
    Optional<ProfileAdditionalInfo> findByProfileid(Long profileId);

    // Delete additional info by profileId
    void deleteByProfileid(Long profileId);

    // Check if additional info exists for a profile
    boolean existsByProfileid(Long profileId);
}
