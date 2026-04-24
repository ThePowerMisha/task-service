package com.tpm.task_service.dto;


import com.tpm.task_service.type.TaskStatus;

public record TaskDto(
        Long id,
        String title,
        String description,
        TaskStatus status,
        UserDto user
) {}
