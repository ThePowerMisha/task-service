package com.tpm.task_service.dto;

import com.tpm.task_service.type.TaskStatus;

public record ChangeTaskStatusRequest(
        TaskStatus status
) {
}
