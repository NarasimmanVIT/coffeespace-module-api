package com.coffeespace.repository;

import com.coffeespace.entity.ProfileExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileExperienceRepository extends JpaRepository<ProfileExperience, Long> {

    List<ProfileExperience> findByProfileid(Long profileid); // existing

    void deleteByProfileid(Long profileid); // existing

    // ✅ Get the latest experience by start date (descending)
    Optional<ProfileExperience> findTop1ByProfileidOrderByStartdateDesc(Long profileid);
}
