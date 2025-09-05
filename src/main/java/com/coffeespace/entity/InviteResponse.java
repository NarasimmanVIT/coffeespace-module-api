package com.coffeespace.entity;

import com.coffeespace.entity.common.BaseEntity;
import com.coffeespace.enums.InviteStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invite_response")
public class InviteResponse extends BaseEntity {

    @Column(name = "invite_id", nullable = false)
    private Long inviteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "response_type", nullable = false)
    private InviteStatus responseType;

    @Column(name = "responder_profile_id", nullable = false)
    private Long responderProfileId;
}
