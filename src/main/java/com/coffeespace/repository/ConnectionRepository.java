package com.coffeespace.repository;

import com.coffeespace.entity.Connection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    boolean existsByProfile1IdAndProfile2Id(Long profile1Id, Long profile2Id);
    boolean existsByProfile2IdAndProfile1Id(Long profile1Id, Long profile2Id);
    Page<Connection> findByProfile1IdOrProfile2Id(Long profile1Id, Long profile2Id, Pageable pageable);
}

