package org.kaspi.labmodule2project1.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum DeliveryStatus {
    CREATED,
    PROCESSING,
    DISPATCHED,
    IN_TRANSIT,
    DELIVERED,
    FAILED,
    CANCELED
}

