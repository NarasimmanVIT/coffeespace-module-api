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

    // ===== Model → Entity =====
    public List<ProfileExperience> modelToEntities(Long profileId, List<LinkedInExperienceDTO> dtos) {
        log.debug("Converting LinkedInExperienceDTO list to ProfileExperience entities");
        return dtos == null ? List.of() : dtos.stream().map(dto -> {
            ProfileExperience pe = new ProfileExperience();
            pe.setProfileid(profileId);
            pe.setRole(dto.getTitle());
            pe.setCompany(dto.getCompany());
            pe.setStartdate(dto.getStartDate());
            pe.setEnddate(dto.getEndDate());
            pe.setLocation(dto.getLocation());
            return pe;
        }).collect(Collectors.toList());
    }

    // ===== Entity → Model =====
    public List<LinkedInExperienceDTO> entityToModels(List<ProfileExperience> entities) {
        log.debug("Converting ProfileExperience entities to LinkedInExperienceDTO list");
        return entities == null ? List.of() : entities.stream().map(pe ->
                LinkedInExperienceDTO.builder()
                        .title(pe.getRole())
                        .company(pe.getCompany())
                        .startDate(pe.getStartdate())
                        .endDate(pe.getEnddate())
                        .location(pe.getLocation())
                        .isCurrent(pe.getEnddate() == null) // assuming null end date = current job
                        .build()
        ).collect(Collectors.toList());
    }

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
