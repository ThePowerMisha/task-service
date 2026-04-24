package com.tpm.task_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AssignTaskRequest(
        @NotNull UUID userId
) {
}
