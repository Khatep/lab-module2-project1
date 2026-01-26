package org.kaspi.labmodule2project1.domain.dto.request;

import org.kaspi.labmodule2project1.domain.enums.DeliveryStatus;

public record CreateDeliveryForProductRequestDto(Long productId, String address, DeliveryStatus status) {}
