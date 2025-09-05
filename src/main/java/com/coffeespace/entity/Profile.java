package com.coffeespace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.coffeespace.entity.common.BaseEntity;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile extends BaseEntity {


    private String firstname;
    private String lastname;
    @Column(name = "contact_number", unique = true)
    private String contactNumber;
    private String email;
    @Column(name = "dob")
    private LocalDate dob;
    private Integer age;
    private String city;
    private String profilePicUrl;
}
