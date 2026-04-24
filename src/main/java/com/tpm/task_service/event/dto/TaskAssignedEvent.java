package com.tpm.task_service.event.dto;

import java.time.Instant;
import java.util.UUID;

public record TaskAssignedEvent(
        Long taskId,
        UUID userId,
        Instant assignedAt
) {
}
