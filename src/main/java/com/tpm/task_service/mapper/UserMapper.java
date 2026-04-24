package com.tpm.task_service.mapper;

import com.tpm.task_service.dto.UserDto;
import com.tpm.task_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User entity);

    User toEntity(UserDto dto);
}
