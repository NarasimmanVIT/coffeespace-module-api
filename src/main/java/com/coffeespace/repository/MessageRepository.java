package com.coffeespace.repository;

import com.coffeespace.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByConversationIdOrderBySentAtAsc(String conversationId, Pageable pageable);

    Page<Message> findByConversationIdOrderBySentAtDesc(String conversationId, Pageable pageable);

    long countByConversationIdAndReceiverIdAndIsReadFalse(String conversationId, Long receiverId);

    List<Message> findByConversationIdAndReceiverIdAndIsReadFalse(String conversationId, Long receiverId);
}
