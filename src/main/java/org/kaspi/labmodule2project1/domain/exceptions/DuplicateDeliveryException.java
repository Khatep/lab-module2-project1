package org.kaspi.labmodule2project1.domain.exceptions;

public class DuplicateDeliveryException extends RuntimeException {
    public DuplicateDeliveryException(String message) {
        super(message);
    }
}
