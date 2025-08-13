package com.coffeespace.assembler;

import com.coffeespace.converter.*;
import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationAssembler {

    private final ProfileConverter profileConverter;
    private final ProfileAdditionalInfoConverter additionalInfoConverter;
    private final ProfileSkillSetConverter skillSetConverter;
    private final ProfileInterestedIndustriesConverter industriesConverter;
    private final ProfileExperienceConverter experienceConverter;
    private final ProfileEducationConverter educationConverter;

    public Profile toProfile(RegisterRequest req) {
        return profileConverter.toEntity(req);
    }

    public ProfileAdditionalInfo toAdditionalInfo(Long profileId, RegisterRequest req) {
        return additionalInfoConverter.toEntity(profileId, req);
    }

    public ProfileSkillSet toSkillSet(Long profileId, List<String> skills) {
        return skillSetConverter.toEntity(profileId, skills);
    }

    public ProfileInterestedIndustries toIndustries(Long profileId, List<String> industries) {
        return industriesConverter.toEntity(profileId, industries);
    }

    public List<ProfileExperience> toExperiences(Long profileId, List<com.coffeespace.dto.LinkedInExperienceDTO> exps) {
        return experienceConverter.toEntities(profileId, exps);
    }

    public List<ProfileEducation> toEducations(Long profileId, List<com.coffeespace.dto.LinkedInEducationDTO> edus) {
        return educationConverter.toEntities(profileId, edus);
    }
}
