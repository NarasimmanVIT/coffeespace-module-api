package com.coffeespace.controller;

import com.coffeespace.dto.ApiResponse;
import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.dto.RegisterResponse;
import com.coffeespace.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest request) {
        RegisterResponse resp = registrationService.register(request);

        ApiResponse<RegisterResponse> api = ApiResponse.<RegisterResponse>builder()
                .message("User registered successfully")
                .statusCode(201)
                .success(true)
                .data(resp)
                .build();

        return ResponseEntity.status(201).body(api);
    }
}
