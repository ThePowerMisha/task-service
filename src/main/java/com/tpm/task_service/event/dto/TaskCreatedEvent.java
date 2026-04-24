package com.tpm.task_service.event.dto;

import java.time.Instant;
import java.util.UUID;

public record TaskCreatedEvent(
        Long taskId,
        String title,
        String description,
        String status,
        UUID userId,
        Instant createdAt
) {}
