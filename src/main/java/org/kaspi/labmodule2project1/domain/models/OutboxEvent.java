package org.kaspi.labmodule2project1.domain.models;

import io.r2dbc.postgresql.codec.Json;
import lombok.*;
import org.kaspi.labmodule2project1.domain.enums.OutboxStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("outbox_event")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {

    @Id
    private UUID id;

    private String aggregateType;

    private Long aggregateId;

    private String eventType;

    @Column("payload")
    private Json payload;

    private OutboxStatus status;

    private int retryCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public OutboxEvent(String aggregateType, Long aggregateId, String eventType, Json payload) {
        this.id = UUID.randomUUID();
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = OutboxStatus.NEW;
        this.retryCount = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markSent() {
        this.status = OutboxStatus.SENT;
        this.updatedAt = LocalDateTime.now();
    }

    public void markFailed() {
        this.status = OutboxStatus.FAILED;
        this.retryCount++;
        this.updatedAt = LocalDateTime.now();
    }
}
