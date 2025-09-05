package com.coffeespace.service;

import com.coffeespace.dto.PaginatedResponse;
import com.coffeespace.dto.ProfileSummaryResponse;
import com.coffeespace.entity.Invite;
import com.coffeespace.enums.InviteStatus;
import com.coffeespace.repository.InviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final InviteRepository inviteRepository;

    public PaginatedResponse<ProfileSummaryResponse> getReceivedInvites(Long profileId, int page, int size) {
        Page<Invite> invitePage = inviteRepository
                .findByReceiverIdAndStatus(profileId, InviteStatus.PENDING, PageRequest.of(page, size));

        return PaginatedResponse.<ProfileSummaryResponse>builder()
                .items(invitePage.stream().map(inv ->
                                ProfileSummaryResponse.builder()
                                        .id(inv.getId())
                                        .name("Sender Name") // replace with Profile lookup if needed
                                        .role("Role")
                                        .avatar("https://example.com/avatar.jpg")
                                        .message(inv.getMessage())
                                        .tag("connect")
                                        .timeAgo("1 hour ago")
                                        .build())
                        .collect(Collectors.toList()))
                .page(page)
                .size(size)
                .total(invitePage.getTotalElements())
                .totalPages(invitePage.getTotalPages())
                .build();
    }

    public PaginatedResponse<ProfileSummaryResponse> getSentInvites(Long profileId, int page, int size) {
        Page<Invite> invitePage = inviteRepository
                .findBySenderId(profileId, PageRequest.of(page, size));

        return PaginatedResponse.<ProfileSummaryResponse>builder()
                .items(invitePage.stream().map(inv ->
                                ProfileSummaryResponse.builder()
                                        .id(inv.getId())
                                        .name("Target Name") // replace with Profile lookup if needed
                                        .role("Role")
                                        .avatar("https://example.com/avatar.jpg")
                                        .message("Invite sent.")
                                        .status(inv.getStatus().name().toLowerCase())
                                        .sentAt(inv.getSentAt().format(DateTimeFormatter.ISO_DATE))
                                        .build())
                        .collect(Collectors.toList()))
                .page(page)
                .size(size)
                .total(invitePage.getTotalElements())
                .totalPages(invitePage.getTotalPages())
                .build();
    }
}
