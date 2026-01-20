package org.kaspi.labmodule2project1.services;

import org.kaspi.labmodule2project1.domain.models.OutboxEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OutboxService {

    @Autowired
    private R2dbcEntityTemplate template;

    public Mono<Void> saveOutboxEvent(OutboxEvent event) {
        return template.insert(OutboxEvent.class)
                .using(event)
                .then();
    }
}
