    package com.coffeespace.entity;

    import com.coffeespace.entity.common.BaseEntity;
    import com.coffeespace.enums.InteractionType;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;

    @Getter
    @Setter
    @Entity
    @Table(
            name = "interaction",
            uniqueConstraints = @UniqueConstraint(columnNames = {"source_profile_id", "target_profile_id"})
    )
    public class Interaction extends BaseEntity {

        @Column(name = "source_profile_id", nullable = false)
        private Long sourceProfileId;

        @Column(name = "target_profile_id", nullable = false)
        private Long targetProfileId;

        @Enumerated(EnumType.STRING)
        @Column(name = "type", nullable = false)
        private InteractionType type;

        @Column(name = "message")
        private String message ;
    }
