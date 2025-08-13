package com.coffeespace.converter;

import com.coffeespace.entity.ProfileSkillSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProfileSkillSetConverter {

    public ProfileSkillSet toEntity(Long profileId, List<String> skills) {
        log.debug("Converting skill list to ProfileSkillSet entity");
        ProfileSkillSet s = new ProfileSkillSet();
        s.setProfileid(profileId);

        if (skills != null) {
            if (skills.size() > 0) s.setSkill1(skills.get(0).trim());
            if (skills.size() > 1) s.setSkill2(skills.get(1).trim());
            if (skills.size() > 2) s.setSkill3(skills.get(2).trim());
            if (skills.size() > 3) s.setSkill4(skills.get(3).trim());
            if (skills.size() > 4) s.setSkill5(skills.get(4).trim());
        }
        return s;
    }
}
