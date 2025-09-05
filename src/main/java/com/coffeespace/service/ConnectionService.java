package com.coffeespace.service;

import com.coffeespace.dto.PaginatedResponse;
import com.coffeespace.dto.ProfileSummaryResponse;
import com.coffeespace.entity.Connection;
import com.coffeespace.repository.ConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRepository connectionRepository;

    public PaginatedResponse<ProfileSummaryResponse> getConnections(Long profileId, int page, int size) {
        Page<Connection> connPage = connectionRepository
                .findByProfile1IdOrProfile2Id(profileId, profileId, PageRequest.of(page, size));

        return PaginatedResponse.<ProfileSummaryResponse>builder()
                .items(connPage.stream().map(conn ->
                                ProfileSummaryResponse.builder()
                                        .id(conn.getId())
                                        .name("Connected User")
                                        .role("Role")
                                        .avatar("https://example.com/avatar.jpg")
                                        .sentAt(conn.getConnectedAt().format(DateTimeFormatter.ISO_DATE))
                                        .status("connected")
                                        .build())
                        .collect(Collectors.toList()))
                .page(page)
                .size(size)
                .total(connPage.getTotalElements())
                .totalPages(connPage.getTotalPages())
                .build();
    }
}
