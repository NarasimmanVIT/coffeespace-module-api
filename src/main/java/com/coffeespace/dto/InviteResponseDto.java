package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InviteResponseDto {
    private Long id;
    private Long inviteId;
    private String type;
    private Long responderProfileId;
}
