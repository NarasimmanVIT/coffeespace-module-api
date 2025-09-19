package com.coffeespace.service;

import com.coffeespace.dto.RecommendedProfileResponse;
import com.coffeespace.dto.RecommendationsPageResponse;
import com.coffeespace.dto.RegisterRequest;
import com.coffeespace.entity.Profile;
import com.coffeespace.repository.RecommendationRepository;
import com.coffeespace.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileRecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final ProfileRepository profileRepository;
    private final ProfileSkillSetService skillSetService;
    private final ProfileInterestedIndustriesService industriesService;
    private final ProfileAdditionalInfoService additionalInfoService;

    public RecommendationsPageResponse recommendProfiles(Long currentProfileId, int page, int size) {
        log.info("Building recommendations for profileId={} page={} size={}", currentProfileId, page, size);

        Profile me = profileRepository.findById(currentProfileId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found: " + currentProfileId));

        List<String> mySkills = skillSetService.getSkills(currentProfileId);
        List<String> myIndustries = industriesService.getIndustries(currentProfileId);
        RegisterRequest addl = additionalInfoService.getByProfileId(currentProfileId);

        List<String> skills = (mySkills == null || mySkills.isEmpty()) ? List.of("__NONE__") : mySkills;
        List<String> industries = (myIndustries == null || myIndustries.isEmpty()) ? List.of("__NONE__") : myIndustries;

        List<String> priorities = (addl.getPriorities() == null) ? Collections.emptyList() : addl.getPriorities();
        String p1 = priorities.size() > 0 ? priorities.get(0) : "__NONE__";
        String p2 = priorities.size() > 1 ? priorities.get(1) : "__NONE__";
        String p3 = priorities.size() > 2 ? priorities.get(2) : "__NONE__";
        String p4 = priorities.size() > 3 ? priorities.get(3) : "__NONE__";
        String p5 = priorities.size() > 4 ? priorities.get(4) : "__NONE__";
        int prioritiesIsEmpty = priorities.isEmpty() ? 1 : 0;

        int offset = page * size;

        List<Object[]> rows = recommendationRepository.findRecommendations(
                currentProfileId,
                addl.getGoal(),
                addl.getExperience(),
                skills,
                industries,
                prioritiesIsEmpty, p1, p2, p3, p4, p5,
                size, offset
        );

        List<RecommendedProfileResponse> profiles = rows.stream()
                .map(r -> RecommendedProfileResponse.builder()
                        .profileId(((Number) r[0]).longValue())
                        .firstName((String) r[1])
                        .lastName((String) r[2])
                        .email((String) r[3])
                        .city((String) r[4])
                        .age(r[5] != null ? ((Number) r[5]).intValue() : null)
                        .goal((String) r[6])
                        .experience((String) r[7])
                        .skills(r[8] != null ? Arrays.asList(((String) r[8]).split(",")) : List.of())
                        .industries(r[9] != null ? Arrays.asList(((String) r[9]).split(",")) : List.of())
                        .role((String) r[10])  // ✅ added role mapping
                        .score(((Number) r[11]).doubleValue())
                        .build())
                .toList();

        long total = recommendationRepository.countOtherProfiles(currentProfileId);
        int totalPages = (int) Math.ceil((double) total / size);

        return RecommendationsPageResponse.builder()
                .profiles(profiles)
                .page(page)
                .size(size)
                .total(total)
                .totalPages(totalPages)
                .build();
    }
}
