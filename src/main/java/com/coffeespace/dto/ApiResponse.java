package com.coffeespace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private int statusCode;
    private boolean success;
    private T data;
}
