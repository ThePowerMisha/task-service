package com.tpm.task_service.dto;

import com.tpm.task_service.type.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangeTaskStatusRequest(
        @NotNull TaskStatus status
) {
}
