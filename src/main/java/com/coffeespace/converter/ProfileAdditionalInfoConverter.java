package com.coffeespace.converter;

import com.coffeespace.dto.ProfileUpdateRequest;
import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.entity.ProfileAdditionalInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProfileAdditionalInfoConverter implements Converter<RegisterRequest, ProfileAdditionalInfo> {

    // ------------------------------
    // Registration mappings
    // ------------------------------

    @Override
    public ProfileAdditionalInfo modelToEntity(RegisterRequest req) {
        log.debug("Converting RegisterRequest to ProfileAdditionalInfo entity");
        ProfileAdditionalInfo info = new ProfileAdditionalInfo();
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

    public ProfileAdditionalInfo modelToEntity(Long profileId, RegisterRequest req) {
        ProfileAdditionalInfo info = modelToEntity(req);
        info.setProfileid(profileId);
        return info;
    }

    // ------------------------------
    // Update mappings
    // ------------------------------

    public ProfileAdditionalInfo modelToEntity(Long profileId, ProfileUpdateRequest req) {
        log.debug("Creating new ProfileAdditionalInfo entity from ProfileUpdateRequest");
        ProfileAdditionalInfo info = new ProfileAdditionalInfo();
        info.setProfileid(profileId);
        if (req.getGoal() != null) info.setGoal(req.getGoal());
        if (req.getExperience() != null) info.setExperience(req.getExperience());
        if (req.getLinkedInProfileUrl() != null) info.setLinkedinProfileUrl(req.getLinkedInProfileUrl());
        if (req.getLinkedInName() != null) info.setLinkedinName(req.getLinkedInName());
        if (req.getLinkedInSummary() != null) info.setLinkedInSummary(req.getLinkedInSummary());
        if (req.getLinkedInConnectionsCount() != null) info.setLinkedInConnectionsCount(req.getLinkedInConnectionsCount());
        if (req.getPriorities() != null) info.setPriorities(String.join(",", req.getPriorities()));
        return info;
    }

    public void updateEntity(ProfileAdditionalInfo info, ProfileUpdateRequest req) {
        if (req.getGoal() != null) info.setGoal(req.getGoal());
        if (req.getExperience() != null) info.setExperience(req.getExperience());
        if (req.getLinkedInProfileUrl() != null) info.setLinkedinProfileUrl(req.getLinkedInProfileUrl());
        if (req.getLinkedInName() != null) info.setLinkedinName(req.getLinkedInName());
        if (req.getLinkedInSummary() != null) info.setLinkedInSummary(req.getLinkedInSummary());
        if (req.getLinkedInConnectionsCount() != null) info.setLinkedInConnectionsCount(req.getLinkedInConnectionsCount());
        if (req.getPriorities() != null) info.setPriorities(String.join(",", req.getPriorities()));
    }

    // ------------------------------
    // Entity to DTO mapping
    // ------------------------------

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
}
