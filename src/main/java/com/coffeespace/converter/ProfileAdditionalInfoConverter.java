package com.coffeespace.converter;

import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.entity.ProfileAdditionalInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProfileAdditionalInfoConverter implements Converter<RegisterRequest, ProfileAdditionalInfo> {

    @Override
    public ProfileAdditionalInfo modelToEntity(RegisterRequest req) {
        log.debug("Converting RegisterRequest to ProfileAdditionalInfo entity");
        ProfileAdditionalInfo info = new ProfileAdditionalInfo();
        // profileId will be set by service after saving Profile
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

    @Override
    public RegisterRequest entityToModel(ProfileAdditionalInfo entity) {
        log.debug("Converting ProfileAdditionalInfo entity to RegisterRequest (partial)");
        return RegisterRequest.builder()
                .goal(entity.getGoal())
                .experience(entity.getExperience())
                .linkedInProfileUrl(entity.getLinkedinProfileUrl())
                .linkedInName(entity.getLinkedinName())
                .linkedInSummary(entity.getLinkedInSummary())
                .linkedInConnectionsCount(entity.getLinkedInConnectionsCount())
                .priorities(entity.getPriorities() != null
                        ? List.of(entity.getPriorities().split(","))
                        : null)
                .build();
    }

    // If profileId is needed separately:
    public ProfileAdditionalInfo modelToEntity(Long profileId, RegisterRequest req) {
        ProfileAdditionalInfo info = modelToEntity(req);
        info.setProfileid(profileId);
        return info;
    }
}
