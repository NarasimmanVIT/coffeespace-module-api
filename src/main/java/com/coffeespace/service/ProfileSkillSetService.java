package com.coffeespace.service;

import com.coffeespace.entity.ProfileSkillSet;
import com.coffeespace.repository.ProfileSkillSetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileSkillSetService {
    private final ProfileSkillSetRepository repo;

    public void saveSkills(ProfileSkillSet skills) {
        log.info("Saving skills for profileId: {}", skills.getProfileid());
        repo.save(skills);
    }
}
