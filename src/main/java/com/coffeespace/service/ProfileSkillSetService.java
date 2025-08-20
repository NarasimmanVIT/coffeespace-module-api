package com.coffeespace.service;

import com.coffeespace.converter.ProfileSkillSetConverter;
import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.entity.ProfileSkillSet;
import com.coffeespace.repository.ProfileSkillSetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileSkillSetService {

    private final ProfileSkillSetRepository repository;
    private final ProfileSkillSetConverter converter;

    @Transactional
    public void saveSkills(Long profileId, List<String> skills) {
        log.debug("Saving skills for profileId={}", profileId);
        ProfileSkillSet entity = converter.modelToEntity(profileId, skills);
        repository.save(entity);
    }

    public List<String> getSkills(Long profileId) {
        log.debug("Fetching skills for profileId={}", profileId);
        ProfileSkillSet entity = repository.findByProfileid(profileId)
                .orElse(null);
        return entity != null ? converter.entityToModel(entity) : List.of();
    }
}
