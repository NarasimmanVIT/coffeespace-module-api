package com.coffeespace.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyOtpData {
    private String token;
    private String profileId;
}
