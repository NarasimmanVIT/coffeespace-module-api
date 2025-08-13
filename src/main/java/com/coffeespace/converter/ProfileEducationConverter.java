package com.coffeespace.converter;

import com.coffeespace.dto.LinkedInEducationDTO;
import com.coffeespace.entity.ProfileEducation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProfileEducationConverter {

    // ===== Model → Entity =====
    public List<ProfileEducation> modelToEntities(Long profileId, List<LinkedInEducationDTO> dtos) {
        log.debug("Converting LinkedInEducationDTO list to ProfileEducation entities");
        return dtos == null ? List.of() : dtos.stream().map(dto -> {
            ProfileEducation pe = new ProfileEducation();
            pe.setProfileid(profileId);
            pe.setInstitutename(dto.getInstitutionName());
            pe.setDepartment(dto.getFieldOfStudy());
            pe.setDegree(dto.getDegree());
            pe.setStartdate(dto.getStartYear() != null ? dto.getStartYear().toString() : null);
            pe.setEnddate(dto.getEndYear() != null ? dto.getEndYear().toString() : null);
            return pe;
        }).collect(Collectors.toList());
    }

    // ===== Entity → Model =====
    public List<LinkedInEducationDTO> entityToModels(List<ProfileEducation> entities) {
        log.debug("Converting ProfileEducation entities to LinkedInEducationDTO list");
        return entities == null ? List.of() : entities.stream().map(pe ->
                LinkedInEducationDTO.builder()
                        .institutionName(pe.getInstitutename())
                        .fieldOfStudy(pe.getDepartment())
                        .degree(pe.getDegree())
                        .startYear(pe.getStartdate() != null ? Integer.valueOf(pe.getStartdate()) : null)
                        .endYear(pe.getEnddate() != null ? Integer.valueOf(pe.getEnddate()) : null)
                        .build()
        ).collect(Collectors.toList());
    }
}
