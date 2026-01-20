package org.kaspi.labmodule2project1.repositories;

import org.kaspi.labmodule2project1.domain.enums.OutboxStatus;
import org.kaspi.labmodule2project1.domain.models.OutboxEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface OutboxEventRepository extends ReactiveCrudRepository<OutboxEvent, UUID> {

    Flux<OutboxEvent> findAllByStatusOrderByCreatedAt(OutboxStatus status);
}
