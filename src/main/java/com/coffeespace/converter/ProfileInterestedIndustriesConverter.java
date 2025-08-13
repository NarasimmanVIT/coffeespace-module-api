package com.coffeespace.converter;

import com.coffeespace.entity.ProfileInterestedIndustries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProfileInterestedIndustriesConverter {

    public ProfileInterestedIndustries toEntity(Long profileId, List<String> industries) {
        log.debug("Converting industry list to ProfileInterestedIndustries entity");
        ProfileInterestedIndustries ind = new ProfileInterestedIndustries();
        ind.setProfileid(profileId);

        if (industries != null) {
            if (industries.size() > 0) ind.setInterest1(industries.get(0).trim());
            if (industries.size() > 1) ind.setInterest2(industries.get(1).trim());
            if (industries.size() > 2) ind.setInterest3(industries.get(2).trim());
            if (industries.size() > 3) ind.setInterest4(industries.get(3).trim());
            if (industries.size() > 4) ind.setInterest5(industries.get(4).trim());
        }
        return ind;
    }
}
