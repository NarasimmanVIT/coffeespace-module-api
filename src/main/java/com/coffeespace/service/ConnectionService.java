package com.coffeespace.service;

import com.coffeespace.dto.PaginatedResponse;
import com.coffeespace.dto.ProfileSummaryResponse;
import com.coffeespace.entity.Connection;
import com.coffeespace.entity.Profile;
import com.coffeespace.repository.ConnectionRepository;
import com.coffeespace.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final ProfileRepository profileRepository;

    public PaginatedResponse<ProfileSummaryResponse> getConnections(Long profileId, int page, int size) {
        Page<Connection> connPage = connectionRepository
                .findByProfile1IdOrProfile2Id(profileId, profileId, PageRequest.of(page, size));

        return PaginatedResponse.<ProfileSummaryResponse>builder()
                .items(connPage.stream().map(conn -> {
                            Long otherProfileId = conn.getProfile1Id().equals(profileId) ? conn.getProfile2Id() : conn.getProfile1Id();
                            Profile otherProfile = profileRepository.findById(otherProfileId).orElse(null);

                            return ProfileSummaryResponse.builder()
                                    .id(otherProfile != null ? otherProfile.getId() : null)
                                    .name(otherProfile != null ? otherProfile.getFirstname() + " " + otherProfile.getLastname() : "Unknown User")
                                    .avatar(otherProfile != null ? otherProfile.getProfilePicUrl() : "https://example.com/default-avatar.jpg")
                                    .status("connected")
                                    .sentAt(conn.getConnectedAt() != null
                                            ? conn.getConnectedAt().format(DateTimeFormatter.ISO_DATE_TIME)
                                            : null)
                                    .timeAgo(conn.getConnectedAt() != null ? calculateTimeAgo(conn.getConnectedAt()) : null)
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .page(page)
                .size(size)
                .total(connPage.getTotalElements())
                .totalPages(connPage.getTotalPages())
                .build();
    }

    private String calculateTimeAgo(LocalDateTime connectedAt) {
        Duration duration = Duration.between(connectedAt, LocalDateTime.now());
        if (duration.toMinutes() < 60) {
            return duration.toMinutes() + " minutes ago";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + " hours ago";
        } else {
            return duration.toDays() + " days ago";
        }
    }
}
