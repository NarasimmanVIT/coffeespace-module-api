package com.coffeespace.converter;

import com.coffeespace.entity.ProfileSkillSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ProfileSkillSetConverter {

    // ===== Model → Entity =====
    public ProfileSkillSet modelToEntity(Long profileId, List<String> skills) {
        log.debug("Converting skill list to ProfileSkillSet entity");
        ProfileSkillSet entity = new ProfileSkillSet();
        entity.setProfileid(profileId);

        // Normalize to exactly 5 skills
        List<String> normalized = normalizeList(skills, 5);

        entity.setSkill1(normalized.get(0));
        entity.setSkill2(normalized.get(1));
        entity.setSkill3(normalized.get(2));
        entity.setSkill4(normalized.get(3));
        entity.setSkill5(normalized.get(4));

        return entity;
    }

    // ===== Entity → Model =====
    public List<String> entityToModel(ProfileSkillSet entity) {
        log.debug("Converting ProfileSkillSet entity to skill list");
        List<String> skills = new ArrayList<>();
        if (entity.getSkill1() != null) skills.add(entity.getSkill1());
        if (entity.getSkill2() != null) skills.add(entity.getSkill2());
        if (entity.getSkill3() != null) skills.add(entity.getSkill3());
        if (entity.getSkill4() != null) skills.add(entity.getSkill4());
        if (entity.getSkill5() != null) skills.add(entity.getSkill5());
        return skills;
    }

    // Utility: Pad/truncate to N elements
    private List<String> normalizeList(List<String> list, int size) {
        List<String> result = new ArrayList<>();
        if (list != null) {
            for (String item : list) {
                if (result.size() == size) break;
                result.add(item != null ? item.trim() : null);
            }
        }
        while (result.size() < size) {
            result.add(null);
        }
        return result;
    }

    public void updateEntity(ProfileSkillSet s, List<String> skills) {
        if (skills == null) return;
        // clear existing first
        s.setSkill1(null);
        s.setSkill2(null);
        s.setSkill3(null);
        s.setSkill4(null);
        s.setSkill5(null);
        for (int i = 0; i < Math.min(5, skills.size()); i++) {
            String v = skills.get(i) != null ? skills.get(i).trim() : null;
            switch (i) {
                case 0 -> s.setSkill1(v);
                case 1 -> s.setSkill2(v);
                case 2 -> s.setSkill3(v);
                case 3 -> s.setSkill4(v);
                case 4 -> s.setSkill5(v);
            }
        }
    }
}
