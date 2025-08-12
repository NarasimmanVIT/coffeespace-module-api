package com.coffeespace.service;

import com.coffeespace.entity.ProfileAdditionalInfo;
import com.coffeespace.repository.ProfileAdditionalInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileAdditionalInfoService {
    private final ProfileAdditionalInfoRepository repo;

    public void saveAdditionalInfo(ProfileAdditionalInfo info) {
        log.info("Saving additional info for profileId: {}", info.getProfileid());
        repo.save(info);
    }
}
