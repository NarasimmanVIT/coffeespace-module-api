package com.coffeespace.service;

import com.coffeespace.converter.ProfileInterestedIndustriesConverter;
import com.coffeespace.entity.ProfileInterestedIndustries;
import com.coffeespace.repository.ProfileInterestedIndustriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileInterestedIndustriesService {

    private final ProfileInterestedIndustriesRepository repository;
    private final ProfileInterestedIndustriesConverter converter;

    @Transactional
    public void saveIndustries(Long profileId, List<String> industries) {
        log.debug("Saving industries for profileId={}", profileId);
        ProfileInterestedIndustries entity = converter.modelToEntity(profileId, industries);
        repository.save(entity);
    }

    public List<String> getIndustries(Long profileId) {
        log.debug("Fetching industries for profileId={}", profileId);
        ProfileInterestedIndustries entity = repository.findByProfileid(profileId)
                .orElse(null);
        return entity != null ? converter.entityToModel(entity) : List.of();
    }

    @Transactional
    public void updateIndustries(Long profileId, List<String> industries) {
        log.info("Updating industries for profileId={}", profileId);
        ProfileInterestedIndustries entity = repository.findByProfileid(profileId)
                .orElseGet(() -> {
                    ProfileInterestedIndustries ind = new ProfileInterestedIndustries();
                    ind.setProfileid(profileId);
                    return ind;
                });
        converter.updateEntity(entity, industries);
        repository.save(entity);
    }
}
