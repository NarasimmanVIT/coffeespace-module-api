package com.coffeespace.entity;

import com.coffeespace.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "conversation")
public class Conversation extends BaseEntity {

    @Column(name = "profile1_id", nullable = false)
    private Long profile1Id;

    @Column(name = "profile2_id", nullable = false)
    private Long profile2Id;

    @Column(name = "last_message")
    private String lastMessage;

    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;
}
