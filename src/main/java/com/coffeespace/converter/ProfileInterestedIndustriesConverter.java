package com.coffeespace.converter;

import com.coffeespace.entity.ProfileInterestedIndustries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ProfileInterestedIndustriesConverter {

    // ===== Model → Entity =====
    public ProfileInterestedIndustries modelToEntity(Long profileId, List<String> industries) {
        log.debug("Converting industry list to ProfileInterestedIndustries entity");
        ProfileInterestedIndustries entity = new ProfileInterestedIndustries();
        entity.setProfileid(profileId);

        // Ensure list is never null and has exactly 5 elements
        List<String> normalized = normalizeList(industries, 5);

        entity.setInterest1(normalized.get(0));
        entity.setInterest2(normalized.get(1));
        entity.setInterest3(normalized.get(2));
        entity.setInterest4(normalized.get(3));
        entity.setInterest5(normalized.get(4));

        return entity;
    }

    // ===== Entity → Model =====
    public List<String> entityToModel(ProfileInterestedIndustries entity) {
        log.debug("Converting ProfileInterestedIndustries entity to industry list");
        List<String> industries = new ArrayList<>();
        if (entity.getInterest1() != null) industries.add(entity.getInterest1());
        if (entity.getInterest2() != null) industries.add(entity.getInterest2());
        if (entity.getInterest3() != null) industries.add(entity.getInterest3());
        if (entity.getInterest4() != null) industries.add(entity.getInterest4());
        if (entity.getInterest5() != null) industries.add(entity.getInterest5());
        return industries;
    }

    // Utility: pad/truncate to size N
    private List<String> normalizeList(List<String> list, int size) {
        List<String> result = new ArrayList<>();
        if (list != null) {
            for (String item : list) {
                if (result.size() == size) break;   // Truncate
                result.add(item != null ? item.trim() : null);
            }
        }
        // Pad with nulls if less than size
        while (result.size() < size) {
            result.add(null);
        }
        return result;
    }
}
