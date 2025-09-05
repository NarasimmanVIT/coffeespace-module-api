package com.coffeespace.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileSummaryResponse {
    private Long id;
    private String name;
    private String role;
    private String avatar;
    private String message;
    private String tag;
    private String timeAgo;
    private String status; // For sent invites or connections
    private String sentAt; // For sent invites
}
