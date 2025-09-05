package com.coffeespace.repository;

import com.coffeespace.entity.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    Optional<Interaction> findBySourceProfileIdAndTargetProfileId(Long sourceId, Long targetId);
}
