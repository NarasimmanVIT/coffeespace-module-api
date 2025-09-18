package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileSummaryResponse {
    private Long id;
    private String name;
    private String role;
    private String avatar;
    private String message;
    private String sentAt;
    private String status;
    private String tag;
    private String timeAgo;
}
