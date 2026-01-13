package org.kaspi.labmodule2project1.services;

import lombok.RequiredArgsConstructor;
import org.kaspi.labmodule2project1.domain.models.OutboxEvent;
import org.kaspi.labmodule2project1.repositories.OutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxEventRepository outboxEventRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOutboxEvent(OutboxEvent event) {
        outboxEventRepository.save(event);
    }
}
