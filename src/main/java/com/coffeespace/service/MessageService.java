package com.coffeespace.service;

import com.coffeespace.dto.MessageRequest;
import com.coffeespace.dto.MessageResponse;
import com.coffeespace.dto.PaginatedMessageResponse;
import com.coffeespace.entity.Message;
import com.coffeespace.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate; // ✅ WebSocket broadcaster

    public PaginatedMessageResponse getMessages(String conversationId, int page, int size) {
        var pageable = PageRequest.of(page, size);
        Page<Message> messagePage =
                messageRepository.findByConversationIdOrderBySentAtAsc(conversationId, pageable);

        return PaginatedMessageResponse.builder()
                .page(page)
                .size(size)
                .totalElements(messagePage.getTotalElements())
                .totalPages(messagePage.getTotalPages())
                .messages(messagePage.getContent().stream()
                        .map(m -> MessageResponse.builder()
                                .messageId(m.getId().toString())
                                .conversationId(m.getConversationId())
                                .senderId(m.getSenderId())
                                .receiverId(m.getReceiverId())
                                .text(m.getText())
                                .sentAt(m.getSentAt())
                                .isRead(m.getIsRead())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public MessageResponse sendMessage(Long senderId, MessageRequest req) {
        Message msg = new Message();
        msg.setConversationId(req.getConversationId());
        msg.setSenderId(senderId);
        msg.setReceiverId(req.getReceiverId());
        msg.setText(req.getText());

        msg = messageRepository.save(msg);

        MessageResponse response = MessageResponse.builder()
                .messageId(msg.getId().toString())
                .conversationId(msg.getConversationId())
                .senderId(msg.getSenderId())
                .receiverId(msg.getReceiverId())
                .text(msg.getText())
                .sentAt(msg.getSentAt())
                .isRead(msg.getIsRead())
                .build();

        // ✅ Push to WebSocket so all subscribers of this conversation get update
        String destination = "/topic/conversations/" + msg.getConversationId();
        log.info("Broadcasting message {} to {}", response.getMessageId(), destination);
        messagingTemplate.convertAndSend(destination, response);

        return response;
    }
}
