package org.kaspi.labmodule2project1.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kaspi.labmodule2project1.clients.DeliveryServiceClient;
import org.kaspi.labmodule2project1.domain.dto.request.CreateDeliveryForProductRequestDto;
import org.kaspi.labmodule2project1.domain.models.OutboxEvent;
import org.kaspi.labmodule2project1.repositories.OutboxEventRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxProcessor {

    private final OutboxEventRepository outboxEventRepository;
    private final DeliveryServiceClient deliveryServiceClient;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processOutbox() {
        List<OutboxEvent> events = outboxEventRepository.findNewEvents();

        for (OutboxEvent event : events) {
            try {
                CreateDeliveryForProductRequestDto payload =
                        objectMapper.readValue(
                                event.getPayload(),
                                CreateDeliveryForProductRequestDto.class
                        );

                log.info(payload.toString());
                deliveryServiceClient.createDelivery(payload);
                event.markSent();

            } catch (Exception e) {
                log.error("Failed to process outbox event {}", event.getId(), e);
                event.markFailed();
            }
        }
    }
}

