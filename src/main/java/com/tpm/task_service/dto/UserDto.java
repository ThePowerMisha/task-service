package com.tpm.task_service.dto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String name,
        String email
) {}
