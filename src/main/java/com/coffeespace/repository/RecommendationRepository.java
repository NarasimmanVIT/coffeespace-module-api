package com.coffeespace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.coffeespace.entity.Profile;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Profile, Long> {

    @Query(
            value = """
WITH profile_skills AS (
    SELECT profileid, GROUP_CONCAT(skill SEPARATOR ',') as skills
    FROM (
        SELECT profileid, skill1 AS skill FROM profile_skill_set
        UNION ALL
        SELECT profileid, skill2 FROM profile_skill_set
        UNION ALL
        SELECT profileid, skill3 FROM profile_skill_set
        UNION ALL
        SELECT profileid, skill4 FROM profile_skill_set
        UNION ALL
        SELECT profileid, skill5 FROM profile_skill_set
    ) merged_skills
    WHERE skill IS NOT NULL AND skill <> ''
    GROUP BY profileid
),
profile_industries AS (
    SELECT profileid, GROUP_CONCAT(industry SEPARATOR ',') as industries
    FROM (
        SELECT profileid, interest1 AS industry FROM profile_interested_industries
        UNION ALL
        SELECT profileid, interest2 FROM profile_interested_industries
        UNION ALL
        SELECT profileid, interest3 FROM profile_interested_industries
        UNION ALL
        SELECT profileid, interest4 FROM profile_interested_industries
        UNION ALL
        SELECT profileid, interest5 FROM profile_interested_industries
    ) merged_industries
    WHERE industry IS NOT NULL AND industry <> ''
    GROUP BY profileid
),
latest_experience AS (
    SELECT pe.profileid, pe.role
    FROM profile_experience pe
    INNER JOIN (
        SELECT profileid, MAX(startdate) AS max_start
        FROM profile_experience
        GROUP BY profileid
    ) latest ON pe.profileid = latest.profileid AND pe.startdate = latest.max_start
)
SELECT 
    p.id,
    p.firstname,
    p.lastname,
    p.email,
    p.city,
    p.age,
    addl.goal,
    addl.experience,
    ps.skills,
    pi.industries,
    le.role,
    (
        (CASE WHEN addl.goal = :goal THEN 10 ELSE 0 END) +
        (CASE WHEN addl.experience = :experience THEN 8 ELSE 0 END) +
        (
            SELECT COUNT(*) * 5 
            FROM (
                SELECT skill1 AS skill FROM profile_skill_set WHERE profileid = p.id
                UNION ALL
                SELECT skill2 FROM profile_skill_set WHERE profileid = p.id
                UNION ALL
                SELECT skill3 FROM profile_skill_set WHERE profileid = p.id
                UNION ALL
                SELECT skill4 FROM profile_skill_set WHERE profileid = p.id
                UNION ALL
                SELECT skill5 FROM profile_skill_set WHERE profileid = p.id
            ) skills_flat
            WHERE skill IN (:skills)
        ) +
        (
            SELECT COUNT(*) * 4 
            FROM (
                SELECT interest1 AS industry FROM profile_interested_industries WHERE profileid = p.id
                UNION ALL
                SELECT interest2 FROM profile_interested_industries WHERE profileid = p.id
                UNION ALL
                SELECT interest3 FROM profile_interested_industries WHERE profileid = p.id
                UNION ALL
                SELECT interest4 FROM profile_interested_industries WHERE profileid = p.id
                UNION ALL
                SELECT interest5 FROM profile_interested_industries WHERE profileid = p.id
            ) industries_flat
            WHERE industry IN (:industries)
        ) +
        (CASE WHEN :prioritiesEmpty = 1 THEN 0 ELSE
            (CASE WHEN addl.priorities LIKE CONCAT('%', :p1, '%') THEN 2 ELSE 0 END +
             CASE WHEN addl.priorities LIKE CONCAT('%', :p2, '%') THEN 2 ELSE 0 END +
             CASE WHEN addl.priorities LIKE CONCAT('%', :p3, '%') THEN 2 ELSE 0 END +
             CASE WHEN addl.priorities LIKE CONCAT('%', :p4, '%') THEN 2 ELSE 0 END +
             CASE WHEN addl.priorities LIKE CONCAT('%', :p5, '%') THEN 2 ELSE 0 END)
        END)
    ) AS score
FROM profile p
LEFT JOIN profile_additional_info addl ON addl.profileid = p.id
LEFT JOIN profile_skills ps ON ps.profileid = p.id
LEFT JOIN profile_industries pi ON pi.profileid = p.id
LEFT JOIN latest_experience le ON le.profileid = p.id
WHERE p.id <> :currentProfileId
  AND p.id NOT IN (
      SELECT i.target_profile_id 
      FROM interaction i 
      WHERE i.source_profile_id = :currentProfileId 
        AND i.type IN ('DISLIKE', 'LIKE')
  )
GROUP BY p.id, p.firstname, p.lastname, p.email, p.city, p.age, 
         addl.goal, addl.experience, ps.skills, pi.industries, addl.priorities, le.role
ORDER BY score DESC
LIMIT :size OFFSET :offset
""",
            nativeQuery = true
    )
    List<Object[]> findRecommendations(
            @Param("currentProfileId") Long currentProfileId,
            @Param("goal") String goal,
            @Param("experience") String experience,
            @Param("skills") List<String> skills,
            @Param("industries") List<String> industries,
            @Param("prioritiesEmpty") int prioritiesEmpty,
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
