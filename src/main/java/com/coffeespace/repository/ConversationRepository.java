package com.coffeespace.repository;

import com.coffeespace.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByProfile1IdAndProfile2Id(Long p1, Long p2);
    List<Conversation> findByProfile1IdOrProfile2Id(Long profile1Id, Long profile2Id);
}
