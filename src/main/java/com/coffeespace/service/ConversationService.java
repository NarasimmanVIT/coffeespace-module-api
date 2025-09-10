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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConnectionRepository connectionRepository;
    private final ProfileRepository profileRepository;
    private final MessageRepository messageRepository;

    /**
     * Build conversations from connections (each connection = conversation).
     * conversationId = "conv_" + connection.id
     */
    public PaginatedConversationResponse getConversations(Long profileId, int page, int size) {
        log.info("Fetching conversations for profileId={} page={} size={}", profileId, page, size);

        Page<Connection> connPage = connectionRepository.findByProfile1IdOrProfile2Id(profileId, profileId, PageRequest.of(page, size));

        List<ConversationResponse> list = new ArrayList<>();

        for (Connection conn : connPage.getContent()) {
            Long otherId = conn.getProfile1Id().equals(profileId) ? conn.getProfile2Id() : conn.getProfile1Id();

            Optional<Profile> otherOpt = profileRepository.findById(otherId);

            String conversationId = "conv_" + conn.getId();

            // fetch last message (descending, single)
            Page<Message> lastPage = messageRepository.findByConversationIdOrderBySentAtDesc(conversationId, PageRequest.of(0, 1));
            Message last = lastPage.hasContent() ? lastPage.getContent().get(0) : null;

            long unreadCount = messageRepository.countByConversationIdAndReceiverIdAndIsReadFalse(conversationId, profileId);

            ConversationResponse resp = ConversationResponse.builder()
                    .conversationId(conversationId)
                    .participantId(otherId)
                    .name(otherOpt.map(p -> p.getFirstname() + " " + p.getLastname()).orElse("Unknown User"))
                    .avatar(otherOpt.map(Profile::getProfilePicUrl).orElse(null))
                    .lastMessage(last != null ? last.getText() : "No messages yet")
                    .lastMessageAt(last != null ? last.getSentAt() : conn.getConnectedAt())
                    .unreadCount((int) unreadCount)
                    .isOnline(false)
                    .build();

            list.add(resp);
        }

        return PaginatedConversationResponse.builder()
                .profiles(list)
                .page(page)
                .size(size)
                .totalElements(connPage.getTotalElements())
                .totalPages(connPage.getTotalPages())
                .build();
    }
}
