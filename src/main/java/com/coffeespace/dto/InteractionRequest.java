package com.coffeespace.dto;

import com.coffeespace.enums.InteractionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InteractionRequest {
    private Long targetProfileId;
    private InteractionType type;
    private String message;// LIKE or DISLIKE
}
