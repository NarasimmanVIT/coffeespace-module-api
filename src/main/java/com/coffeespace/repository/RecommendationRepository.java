package com.coffeespace.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coffeespace.entity.Profile;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Profile, Long> {

    @Query(value = """
        SELECT p.id,
               p.first_name,
               p.last_name,
               p.email,
               p.city,
               (
                   (CASE WHEN ai.goal = :goal THEN 20 ELSE 0 END) +
                   (CASE WHEN ai.experience = :experience THEN 20 ELSE 0 END) +
                   (SELECT COUNT(*) * 10
                      FROM profile_skill_set s
                      WHERE s.profileid = p.id
                        AND s.skill IN (:skills)) +
                   (SELECT COUNT(*) * 10
                      FROM profile_interested_industries i
                      WHERE i.profileid = p.id
                        AND i.industry IN (:industries)) +
                   (CASE 
                        WHEN :prioritiesIsEmpty = 1 THEN 0
                        ELSE (
                            (CASE WHEN FIND_IN_SET(:p1, ai.priorities) > 0 THEN 5 ELSE 0 END) +
                            (CASE WHEN FIND_IN_SET(:p2, ai.priorities) > 0 THEN 5 ELSE 0 END) +
                            (CASE WHEN FIND_IN_SET(:p3, ai.priorities) > 0 THEN 5 ELSE 0 END) +
                            (CASE WHEN FIND_IN_SET(:p4, ai.priorities) > 0 THEN 5 ELSE 0 END) +
                            (CASE WHEN FIND_IN_SET(:p5, ai.priorities) > 0 THEN 5 ELSE 0 END)
                        )
                   END)
               ) AS score
        FROM profile p
        LEFT JOIN profile_additional_info ai ON ai.profileid = p.id
        WHERE p.id <> :currentProfileId
        ORDER BY score DESC
        LIMIT :size OFFSET :offset
        """, nativeQuery = true)
    List<Object[]> findRecommendations(
            @Param("currentProfileId") Long currentProfileId,
            @Param("goal") String goal,
            @Param("experience") String experience,
            @Param("skills") List<String> skills,
            @Param("industries") List<String> industries,
            @Param("prioritiesIsEmpty") int prioritiesIsEmpty,
            @Param("p1") String p1,
            @Param("p2") String p2,
            @Param("p3") String p3,
            @Param("p4") String p4,
            @Param("p5") String p5,
            @Param("size") int size,
            @Param("offset") int offset
    );

    @Query(value = "SELECT COUNT(*) FROM profile p WHERE p.id <> :currentProfileId", nativeQuery = true)
    long countOtherProfiles(@Param("currentProfileId") Long currentProfileId);
}
