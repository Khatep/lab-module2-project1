package org.kaspi.labmodule2project1.clients;

import org.kaspi.labmodule2project1.domain.dto.request.CreateDeliveryForProductRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "delivery-service", url = "${feign.clients.kaspi-lab.delivery-service}")
public interface DeliveryServiceClient {
    @PostMapping("/api/v1/delivery")
    ResponseEntity<Void> createDelivery(
            @RequestBody CreateDeliveryForProductRequestDto createDeliveryForProductRequestDto
    );
}
