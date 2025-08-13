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

    public List<ProfileEducation> toEntities(Long profileId, List<LinkedInEducationDTO> edus) {
        log.debug("Converting LinkedInEducationDTO list to ProfileEducation entities");
        return edus == null ? List.of() : edus.stream().map(ed -> {
            ProfileEducation pe = new ProfileEducation();
            pe.setProfileid(profileId);
            pe.setInstitutename(ed.getInstitutionName());
            pe.setDepartment(ed.getFieldOfStudy());
            pe.setDegree(ed.getDegree());
            pe.setStartdate(ed.getStartYear() != null ? ed.getStartYear().toString() : null);
            pe.setEnddate(ed.getEndYear() != null ? ed.getEndYear().toString() : null);
            return pe;
        }).collect(Collectors.toList());
    }
}
