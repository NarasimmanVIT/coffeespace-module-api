package com.coffeespace.converter;

import com.coffeespace.dto.LinkedInExperienceDTO;
import com.coffeespace.entity.ProfileExperience;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProfileExperienceConverter {

    public List<ProfileExperience> toEntities(Long profileId, List<LinkedInExperienceDTO> exps) {
        log.debug("Converting LinkedInExperienceDTO list to ProfileExperience entities");
        return exps == null ? List.of() : exps.stream().map(e -> {
            ProfileExperience pe = new ProfileExperience();
            pe.setProfileid(profileId);
            pe.setRole(e.getTitle());
            pe.setCompany(e.getCompany());
            pe.setStartdate(e.getStartDate());
            pe.setEnddate(e.getEndDate());
            pe.setLocation(e.getLocation());
            return pe;
        }).collect(Collectors.toList());
    }
}
