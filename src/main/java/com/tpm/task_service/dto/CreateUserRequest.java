package com.tpm.task_service.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest (
        @NotBlank String name,
        @NotBlank String email
){
}
