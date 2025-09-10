package com.coffeespace.service;

import com.coffeespace.dto.MessageRequest;
import com.coffeespace.dto.MessageResponse;
import com.coffeespace.dto.PaginatedMessageResponse;
import com.coffeespace.entity.Connection;
import com.coffeespace.entity.Message;
import com.coffeespace.repository.ConnectionRepository;
import com.coffeespace.repository.MessageRepository;
import com.coffeespace.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConnectionRepository connectionRepository;
    private final ProfileRepository profileRepository;

    /**
     * Fetch paginated messages for conversationId (conv_<connectionId>),
     * enforce that requester is participant, mark unread messages as read.
     */
    @Transactional
    public PaginatedMessageResponse getMessages(Long requesterProfileId, String conversationId, int page, int size) {
        Long connId = parseConnectionId(conversationId);

        Connection conn = connectionRepository.findById(connId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found"));

        if (!conn.getProfile1Id().equals(requesterProfileId) && !conn.getProfile2Id().equals(requesterProfileId)) {
            throw new IllegalArgumentException("Not authorized to view this conversation");
        }

        Page<Message> msgPage = messageRepository.findByConversationIdOrderBySentAtAsc(conversationId, PageRequest.of(page, size));

        // mark unread messages (where receiver == requester) as read
        List<Message> toMark = msgPage.stream()
                .filter(m -> !m.getIsRead() && m.getReceiverId().equals(requesterProfileId))
                .collect(Collectors.toList());

        if (!toMark.isEmpty()) {
            toMark.forEach(m -> m.setIsRead(true));
            messageRepository.saveAll(toMark);
            log.info("Marked {} messages as read for conversation={} profileId={}", toMark.size(), conversationId, requesterProfileId);
        }

        List<MessageResponse> items = msgPage.stream()
                .map(m -> MessageResponse.builder()
                        .messageId("msg_" + m.getId())
                        .conversationId(m.getConversationId())
                        .senderId(m.getSenderId())
                        .receiverId(m.getReceiverId())
                        .text(m.getText())
                        .sentAt(m.getSentAt())
                        .isRead(m.getIsRead())
                        .build())
                .collect(Collectors.toList());

        return PaginatedMessageResponse.builder()
                .messages(items)
                .page(page)
                .size(size)
                .totalElements(msgPage.getTotalElements())
                .totalPages(msgPage.getTotalPages())
                .build();
    }

    /**
     * Send new message. Ensure sender is connected to receiver (via connection table).
     * If connection doesn't exist between two profiles, reject.
     */
    @Transactional
    public MessageResponse sendMessage(Long senderProfileId, MessageRequest req) {
        if (req.getConversationId() == null || req.getReceiverId() == null || req.getText() == null) {
            throw new IllegalArgumentException("Invalid request");
        }

        // Ensure connection exists between sender and receiver (either direction)
        boolean connected = connectionRepository.existsByProfile1IdAndProfile2Id(senderProfileId, req.getReceiverId())
                || connectionRepository.existsByProfile2IdAndProfile1Id(senderProfileId, req.getReceiverId());

        if (!connected) {
            throw new IllegalArgumentException("You are not connected with this user");
        }

        // Ensure conversation exists (conv_<connectionId>) OR allow creating if conversation is new.
        Long connId = parseConnectionId(req.getConversationId());
        Connection conn = connectionRepository.findById(connId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation (connection) not found"));

        // Persist message
        Message m = new Message();
        m.setConversationId(req.getConversationId());
        m.setSenderId(senderProfileId);
        m.setReceiverId(req.getReceiverId());
        m.setText(req.getText());
        m.setIsRead(false);
        Message saved = messageRepository.save(m);

        // Optionally update connection's last message metadata -> you can extend Connection entity or keep separate Conversation table.
        // (Left as a TODO if you want to add last_message and last_message_at on a separate Conversation table)

        return MessageResponse.builder()
                .messageId("msg_" + saved.getId())
                .conversationId(saved.getConversationId())
                .senderId(saved.getSenderId())
                .receiverId(saved.getReceiverId())
                .text(saved.getText())
                .sentAt(saved.getSentAt())
                .isRead(saved.getIsRead())
                .build();
    }

    // Helper: parse "conv_<id>" -> id
    private Long parseConnectionId(String conversationId) {
        if (conversationId == null || !conversationId.startsWith("conv_")) {
            throw new IllegalArgumentException("Invalid conversationId format");
        }
        String tail = conversationId.substring("conv_".length());
        try {
            return Long.parseLong(tail);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid conversationId");
        }
    }
}
