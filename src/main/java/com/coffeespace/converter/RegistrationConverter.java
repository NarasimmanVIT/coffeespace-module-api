package com.coffeespace.converter;

import com.coffeespace.dto.*;
import com.coffeespace.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RegistrationConverter {

    public Profile toProfileEntity(RegisterRequest req) {
        log.debug("Converting RegisterRequest to Profile entity");
        Profile p = new Profile();
        p.setFirstname(req.getFirstName());
        p.setLastname(req.getLastName());
        p.setEmail(req.getEmail());
        p.setDob(String.valueOf(req.getDob()));
        return p;
    }

    public ProfileAdditionalInfo toAdditionalInfoEntity(Long profileId, RegisterRequest req) {
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

    public ProfileSkillSet toSkillSetEntity(Long profileId, List<String> skills) {
        log.debug("Converting skill list to ProfileSkillSet entity");
        ProfileSkillSet s = new ProfileSkillSet();
        s.setProfileid(profileId);
        if (skills != null) {
            for (int i = 0; i < Math.min(5, skills.size()); i++) {
                String v = skills.get(i).trim();
                switch (i) {
                    case 0 -> s.setSkill1(v);
                    case 1 -> s.setSkill2(v);
                    case 2 -> s.setSkill3(v);
                    case 3 -> s.setSkill4(v);
                    case 4 -> s.setSkill5(v);
                }
            }
        }
        return s;
    }

    public ProfileInterestedIndustries toIndustriesEntity(Long profileId, List<String> industries) {
        log.debug("Converting industry list to ProfileInterestedIndustries entity");
        ProfileInterestedIndustries ind = new ProfileInterestedIndustries();
        ind.setProfileid(profileId);
        if (industries != null) {
            for (int i = 0; i < Math.min(5, industries.size()); i++) {
                String v = industries.get(i).trim();
                switch (i) {
                    case 0 -> ind.setInterest1(v);
                    case 1 -> ind.setInterest2(v);
                    case 2 -> ind.setInterest3(v);
                    case 3 -> ind.setInterest4(v);
                    case 4 -> ind.setInterest5(v);
                }
            }
        }
        return ind;
    }

    public List<ProfileExperience> toExperienceEntities(Long profileId, List<LinkedInExperienceDTO> exps) {
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

    public List<ProfileEducation> toEducationEntities(Long profileId, List<LinkedInEducationDTO> edus) {
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
