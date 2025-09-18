package com.coffeespace.service;

import com.coffeespace.dto.ConversationResponse;
import com.coffeespace.dto.PaginatedConversationResponse;
import com.coffeespace.entity.Connection;
import com.coffeespace.entity.Message;
import com.coffeespace.entity.Profile;
import com.coffeespace.repository.ConnectionRepository;
import com.coffeespace.repository.MessageRepository;
import com.coffeespace.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConnectionRepository connectionRepository;
    private final ProfileRepository profileRepository;
    private final MessageRepository messageRepository;
    private final PresenceService presenceService;

    public PaginatedConversationResponse getConversations(Long profileId, int page, int size) {
        var pageable = PageRequest.of(page, size);
        var connections = connectionRepository.findByProfile1IdOrProfile2Id(profileId, profileId, pageable);

        List<ConversationResponse> responses = connections.stream().map(conn -> {
            Long otherId = conn.getProfile1Id().equals(profileId) ? conn.getProfile2Id() : conn.getProfile1Id();
            Profile otherProfile = profileRepository.findById(otherId).orElse(null);

            String conversationId = "conv_" + conn.getId();
            Message lastMsg = messageRepository.findTop1ByConversationIdOrderBySentAtDesc(conversationId);
            long unreadCount = messageRepository.countByConversationIdAndReceiverIdAndIsReadFalse(conversationId, profileId);

            return ConversationResponse.builder()
                    .conversationId(conversationId)
                    .participantId(otherId)
                    .name(otherProfile != null ? otherProfile.getFirstname() + " " + otherProfile.getLastname() : "Unknown")
                    .avatar(otherProfile != null ? otherProfile.getProfilePicUrl() : null)
                    .lastMessage(lastMsg != null ? lastMsg.getText() : null)
                    .lastMessageAt(lastMsg != null ? lastMsg.getSentAt() : null)
                    .unreadCount((int) unreadCount)
                    .isOnline(presenceService.isOnline(otherId))
                    .build();
        }).collect(Collectors.toList());

        return PaginatedConversationResponse.builder()
                .page(page)
                .size(size)
                .totalElements(connections.getTotalElements())
                .totalPages(connections.getTotalPages())
                .profiles(responses)
                .build();
    }
}
