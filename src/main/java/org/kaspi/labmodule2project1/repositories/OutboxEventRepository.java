package org.kaspi.labmodule2project1.repositories;

import org.kaspi.labmodule2project1.domain.models.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        select e from OutboxEvent e
        where e.status = 'NEW'
        order by e.createdAt
        """)
    List<OutboxEvent> findNewEvents();
}

