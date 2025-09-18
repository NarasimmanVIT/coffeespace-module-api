package com.coffeespace.controller;

import com.coffeespace.dto.MessageRequest;
import com.coffeespace.dto.MessageResponse;
import com.coffeespace.dto.TypingIndicator;
import com.coffeespace.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(MessageRequest req) {
        log.info("📨 Incoming WS message: {}", req);
        MessageResponse response = messageService.sendMessage(req.getReceiverId(), req);
        messagingTemplate.convertAndSend("/topic/conversations/" + req.getConversationId(), response);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(req.getReceiverId()),
                "/queue/messages",
                response
        );
    }

//    @MessageMapping("/chat.typing")
//    public void sendTypingIndicator(TypingIndicator typingIndicator) {
//        log.info("✍️ Typing indicator: {}", typingIndicator);
//        messagingTemplate.convertAndSendToUser(
//                String.valueOf(typingIndicator.getReceiverId()),
//                "/queue/typing",
//                typingIndicator
//        );
//    }
}
