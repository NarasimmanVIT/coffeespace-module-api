package com.coffeespace.entity;

import com.coffeespace.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile_experience")
public class ProfileExperience extends BaseEntity {



    private Long profileid;
    private String role;
    private String roleDesc;
    private String company;
    private String startdate;
    private String enddate;

    // Newly added fields
    private String location;
    private Boolean isCurrent;
}
