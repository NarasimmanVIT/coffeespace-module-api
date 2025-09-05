package com.coffeespace.service;

import com.coffeespace.dto.InteractionRequest;
import com.coffeespace.entity.Interaction;
import com.coffeespace.entity.Invite;
import com.coffeespace.enums.InteractionType;
import com.coffeespace.enums.InviteStatus;
import com.coffeespace.repository.InteractionRepository;
import com.coffeespace.repository.InviteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InteractionService {

    private final InteractionRepository interactionRepository;
    private final InviteRepository inviteRepository;

    @Transactional
    public void handleInteraction(Long sourceProfileId, InteractionRequest req) {
        Optional<Interaction> existing = interactionRepository
                .findBySourceProfileIdAndTargetProfileId(sourceProfileId, req.getTargetProfileId());

        if (existing.isPresent()) {
            log.info("Duplicate interaction ignored for source={} target={}", sourceProfileId, req.getTargetProfileId());
            return;
        }

        if (req.getMessage() == null || req.getMessage().isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or blank");
        }

        Interaction interaction = new Interaction();
        interaction.setSourceProfileId(sourceProfileId);
        interaction.setTargetProfileId(req.getTargetProfileId());
        interaction.setType(req.getType());
        interaction.setMessage(req.getMessage().trim());
        interactionRepository.save(interaction);

        if (req.getType() == InteractionType.LIKE) {
            boolean alreadyInvited = inviteRepository
                    .findAll()
                    .stream()
                    .anyMatch(inv -> inv.getSenderId().equals(sourceProfileId)
                            && inv.getReceiverId().equals(req.getTargetProfileId()));
            if (!alreadyInvited) {
                Invite invite = new Invite();
                invite.setSenderId(sourceProfileId);
                invite.setReceiverId(req.getTargetProfileId());
                invite.setStatus(InviteStatus.PENDING);
                invite.setMessage(req.getMessage());
                inviteRepository.save(invite);
                log.info("Auto invite created from {} to {}", sourceProfileId, req.getTargetProfileId());
            }
        }
    }
}
