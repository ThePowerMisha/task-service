package com.tpm.task_service.dto;

import com.tpm.task_service.type.TaskStatus;
import jakarta.validation.constraints.NotBlank;

public record ChangeTaskStatusRequest(
        @NotBlank TaskStatus status
) {
}
