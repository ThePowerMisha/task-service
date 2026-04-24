package com.tpm.task_service.mapper;

import com.tpm.task_service.dto.TaskDto;
import com.tpm.task_service.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task entity);
    Task toEntity(TaskDto dto);
}
