package com.tpm.task_service.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record AssignTaskRequest(
        @NotBlank UUID userId
) {
}
