package com.coffeespace.entity;

import com.coffeespace.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile_interested_industries")
public class ProfileInterestedIndustries extends BaseEntity {


    private Long profileid;
    private String interest1;
    private String interest2;
    private String interest3;
    private String interest4;
    private String interest5;
}
