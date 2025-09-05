package com.coffeespace.entity;

import com.coffeespace.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "connection",
        uniqueConstraints = @UniqueConstraint(columnNames = {"profile1_id","profile2_id"}))
public class Connection extends BaseEntity {

    @Column(name = "profile1_id", nullable = false)
    private Long profile1Id;

    @Column(name = "profile2_id", nullable = false)
    private Long profile2Id;

    @Column(name = "connected_at", nullable = false)
    private LocalDateTime connectedAt = LocalDateTime.now();
}
