package com.coffeespace.entity;

import com.coffeespace.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile_skill_set")
public class ProfileSkillSet extends BaseEntity {

    private Long profileid;
    private String skill1;
    private String skill2;
    private String skill3;
    private String skill4;
    private String skill5;
}
