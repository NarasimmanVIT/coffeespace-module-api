package com.coffeespace.service;

import com.coffeespace.dto.PaginatedResponse;
import com.coffeespace.dto.ProfileSummaryResponse;
import com.coffeespace.entity.Invite;
import com.coffeespace.entity.Profile;
import com.coffeespace.entity.ProfileExperience;
import com.coffeespace.enums.InviteStatus;
import com.coffeespace.repository.InviteRepository;
import com.coffeespace.repository.ProfileExperienceRepository;
import com.coffeespace.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InviteService {

    private final InviteRepository inviteRepository;
    private final ProfileRepository profileRepository;
    private final ProfileExperienceRepository experienceRepository;

    public PaginatedResponse<ProfileSummaryResponse> getReceivedInvites(Long profileId, int page, int size) {
        Page<Invite> invitePage = inviteRepository
                .findByReceiverIdAndStatus(profileId, InviteStatus.PENDING, PageRequest.of(page, size));

        List<ProfileSummaryResponse> responses = invitePage.stream()
                .map(inv -> {
                    Profile sender = profileRepository.findById(inv.getSenderId()).orElse(null);

                    String name = sender != null ? sender.getFirstname() + " " + sender.getLastname() : "Unknown";
                    String avatar = sender != null ? sender.getProfilePicUrl() : null;

                    String role = null;
                    if (sender != null) {
                        role = experienceRepository.findByProfileid(sender.getId())
                                .stream()
                                .filter(e -> Boolean.TRUE.equals(e.getIsCurrent()))
                                .map(ProfileExperience::getRole)
                                .findFirst()
                                .orElse("Entrepreneur");
                    }

                    return ProfileSummaryResponse.builder()
                            .id(inv.getId())
                            .name(name)
                            .role(role)
                            .avatar(avatar)
                            .message(inv.getMessage())
                            .tag("connect")
                            .timeAgo(inv.getSentAt().format(DateTimeFormatter.ISO_DATE))
                            .build();
                })
                .collect(Collectors.toList());

        return PaginatedResponse.<ProfileSummaryResponse>builder()
                .items(responses)
                .page(page)
                .size(size)
                .total(invitePage.getTotalElements())
                .totalPages(invitePage.getTotalPages())
                .build();
    }

    public PaginatedResponse<ProfileSummaryResponse> getSentInvites(Long profileId, int page, int size) {
        Page<Invite> invitePage = inviteRepository.findBySenderId(profileId, PageRequest.of(page, size));

        List<ProfileSummaryResponse> responses = invitePage.stream()
                .map(inv -> {
                    Profile receiver = profileRepository.findById(inv.getReceiverId()).orElse(null);

                    String name = receiver != null ? receiver.getFirstname() + " " + receiver.getLastname() : "Unknown";
                    String avatar = receiver != null ? receiver.getProfilePicUrl() : null;

                    String role = null;
                    if (receiver != null) {
                        role = experienceRepository.findByProfileid(receiver.getId())
                                .stream()
                                .filter(e -> Boolean.TRUE.equals(e.getIsCurrent()))
                                .map(ProfileExperience::getRole)
                                .findFirst()
                                .orElse("Entrepreneur");
                    }

                    return ProfileSummaryResponse.builder()
                            .id(inv.getId())
                            .name(name)
                            .role(role)
                            .avatar(avatar)
                            .message("Invite sent.")
                            .status(inv.getStatus().name().toLowerCase())
                            .sentAt(inv.getSentAt().format(DateTimeFormatter.ISO_DATE))
                            .build();
                })
                .collect(Collectors.toList());

        return PaginatedResponse.<ProfileSummaryResponse>builder()
                .items(responses)
                .page(page)
                .size(size)
                .total(invitePage.getTotalElements())
                .totalPages(invitePage.getTotalPages())
                .build();
    }
}
