package com.tpm.task_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateTaskRequest(
        @NotBlank @Size(max = 255) String title,
        @Size(max = 5000) String description,
        UUID userId
) {
}
