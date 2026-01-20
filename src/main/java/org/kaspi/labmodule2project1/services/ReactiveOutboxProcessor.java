package org.kaspi.labmodule2project1.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kaspi.labmodule2project1.clients.DeliveryServiceClient;
import org.kaspi.labmodule2project1.domain.dto.request.CreateDeliveryForProductRequestDto;
import org.kaspi.labmodule2project1.domain.enums.OutboxStatus;
import org.kaspi.labmodule2project1.repositories.OutboxEventRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveOutboxProcessor {

    private final OutboxEventRepository outboxEventRepository;
    private final DeliveryServiceClient deliveryServiceClient;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void startProcessing() {
        Flux.interval(Duration.ofSeconds(5))
                .doOnSubscribe(s -> log.info("Reactive Outbox Processor started"))
                .flatMap(tick ->
                        outboxEventRepository.findAllByStatusOrderByCreatedAt(OutboxStatus.NEW)
                                .doOnNext(e -> log.info("Found new outbox event: {}", e.getId()))
                                .flatMap(event ->
                                        Mono.fromCallable(() -> objectMapper.readValue(event.getPayload().asString(), CreateDeliveryForProductRequestDto.class))
                                                .flatMap(payload -> deliveryServiceClient.createDelivery(payload)
                                                        .then(Mono.fromCallable(() -> {
                                                            event.markSent();
                                                            log.info("Outbox event sent: {}", event.getId());
                                                            return event;
                                                        }))
                                                )
                                                .onErrorResume(e -> {
                                                    log.error("Failed to process outbox event {}", event.getId(), e);
                                                    event.markFailed();
                                                    return Mono.just(event);
                                                })
                                                .flatMap(outboxEventRepository::save)
                                )
                )
                .subscribe(
                        null,
                        err -> log.error("Reactive Outbox Processor fatal error", err)
                );
    }


}
