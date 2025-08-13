package com.coffeespace.converter;

import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.entity.ProfileAdditionalInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProfileAdditionalInfoConverter {

    public ProfileAdditionalInfo toEntity(Long profileId, RegisterRequest req) {
        log.debug("Converting RegisterRequest to ProfileAdditionalInfo entity");
        ProfileAdditionalInfo info = new ProfileAdditionalInfo();
        info.setProfileid(profileId);
        info.setGoal(req.getGoal());
        info.setExperience(req.getExperience());
        info.setLinkedinProfileUrl(req.getLinkedInProfileUrl());
        info.setLinkedinName(req.getLinkedInName());
        info.setLinkedInSummary(req.getLinkedInSummary());
        info.setLinkedInConnectionsCount(req.getLinkedInConnectionsCount());
        if (req.getPriorities() != null) {
            info.setPriorities(String.join(",", req.getPriorities()));
        }
        return info;
    }
}
