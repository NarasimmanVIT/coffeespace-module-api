package com.coffeespace.repository;

import com.coffeespace.entity.Invite;
import com.coffeespace.enums.InviteStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    Page<Invite> findByReceiverIdAndStatus(Long receiverId, InviteStatus status, Pageable pageable);
    Page<Invite> findBySenderId(Long senderId, Pageable pageable);
}
