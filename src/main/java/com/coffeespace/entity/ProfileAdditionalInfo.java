package com.coffeespace.entity;

import com.coffeespace.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile_additional_info")
public class ProfileAdditionalInfo extends BaseEntity {



    private Long profileid;
    private String goal;
    private String startupStartedDate;
    private String experience;
    private String linkedinProfileUrl;
    private String linkedinName;
    private String linkedInSummary;
    private Integer linkedInConnectionsCount;
    private String priorities; // stored as CSV
}
