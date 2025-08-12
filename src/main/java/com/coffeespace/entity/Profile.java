package com.coffeespace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.coffeespace.entity.common.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile extends BaseEntity {


    private String firstname;
    private String lastname;
    private String contactNumber;
    private String profilePic;
    private String email;
    private String dob;
    private Integer age;
    private String city;
}
