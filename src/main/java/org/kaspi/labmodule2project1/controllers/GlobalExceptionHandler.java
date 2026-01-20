package org.kaspi.labmodule2project1.controllers;

import org.kaspi.labmodule2project1.domain.exceptions.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleProductNotFoundException(
            ProductNotFoundException ex
    ) {
        Map<String, Object> body = Map.of(
                "timestamp", OffsetDateTime.now(),
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(404).body(body));
    }

}
