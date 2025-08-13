package com.coffeespace.repository;

import com.coffeespace.entity.ProfileSkillSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileSkillSetRepository extends JpaRepository<ProfileSkillSet, Long> {

    // Fetch skills by profileId
    Optional<ProfileSkillSet> findByProfileid(Long profileId);

    // Delete skills by profileId
    void deleteByProfileid(Long profileId);

    // Check if a skill set exists for the given profileId
    boolean existsByProfileid(Long profileId);
}
