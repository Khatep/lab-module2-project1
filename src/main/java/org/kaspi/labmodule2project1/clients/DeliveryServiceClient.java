package org.kaspi.labmodule2project1.clients;

import lombok.RequiredArgsConstructor;
import org.kaspi.labmodule2project1.domain.dto.request.CreateDeliveryForProductRequestDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DeliveryServiceClient {

    private final WebClient deliveryWebClient;

    public Mono<Void> createDelivery(CreateDeliveryForProductRequestDto dto) {
        return deliveryWebClient.post()
                .uri("/api/v1/delivery")
                .bodyValue(dto)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        resp -> Mono.error(new RuntimeException("Delivery service error")))
                .bodyToMono(Void.class);
    }

}
