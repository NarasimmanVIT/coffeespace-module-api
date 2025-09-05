package com.coffeespace.service;

import com.coffeespace.dto.InviteResponseDto;
import com.coffeespace.dto.InviteResponseRequest;
import com.coffeespace.entity.Connection;
import com.coffeespace.entity.Invite;
import com.coffeespace.entity.InviteResponse;

import com.coffeespace.enums.InviteStatus;
import com.coffeespace.repository.ConnectionRepository;
import com.coffeespace.repository.InviteRepository;
import com.coffeespace.repository.InviteResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InviteResponseService {

    private final InviteRepository inviteRepository;
    private final InviteResponseRepository inviteResponseRepository;
    private final ConnectionRepository connectionRepository;

    @Transactional
    public InviteResponseDto handleResponse(Long myProfileId, InviteResponseRequest req) {
        Invite invite = inviteRepository.findById(req.getInviteId())
                .orElseThrow(() -> new IllegalArgumentException("Invite not found"));

        if (!invite.getReceiverId().equals(myProfileId)) {
            throw new IllegalArgumentException("Not authorized to respond to this invite");
        }

        InviteStatus responseType = InviteStatus.valueOf(req.getType().toUpperCase());

        InviteResponse response = new InviteResponse();
        response.setInviteId(req.getInviteId());
        response.setResponseType(responseType);
        response.setResponderProfileId(myProfileId);
        inviteResponseRepository.save(response);

        if (responseType == InviteStatus.ACCEPTED) {
            invite.setStatus(InviteStatus.ACCEPTED);
            inviteRepository.save(invite);

            boolean exists = connectionRepository.existsByProfile1IdAndProfile2Id(invite.getSenderId(), myProfileId)
                    || connectionRepository.existsByProfile2IdAndProfile1Id(invite.getSenderId(), myProfileId);

            if (!exists) {
                Connection connection = new Connection();
                connection.setProfile1Id(invite.getSenderId());
                connection.setProfile2Id(myProfileId);
                connectionRepository.save(connection);
            }
        } else {
            invite.setStatus(InviteStatus.DECLINED);
            inviteRepository.save(invite);
        }

        return InviteResponseDto.builder()
                .id(response.getId())
                .inviteId(response.getInviteId())
                .type(response.getResponseType().name())
                .responderProfileId(response.getResponderProfileId())
                .build();
    }
}
