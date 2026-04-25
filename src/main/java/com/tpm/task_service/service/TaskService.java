package com.tpm.task_service.service;

import com.tpm.task_service.config.KafkaTopicsProperties;
import com.tpm.task_service.dto.AssignTaskRequest;
import com.tpm.task_service.dto.ChangeTaskStatusRequest;
import com.tpm.task_service.dto.CreateTaskRequest;
import com.tpm.task_service.dto.TaskDto;
import com.tpm.task_service.entity.Task;
import com.tpm.task_service.entity.User;
import com.tpm.task_service.event.dto.TaskAssignedEvent;
import com.tpm.task_service.event.dto.TaskCreatedEvent;
import com.tpm.task_service.exception.TaskNotFoundException;
import com.tpm.task_service.exception.UserNotFoundException;
import com.tpm.task_service.mapper.TaskMapper;
import com.tpm.task_service.repository.TaskRepository;
import com.tpm.task_service.repository.UserRepository;
import com.tpm.task_service.type.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final OutboxService outboxService;
    private final KafkaTopicsProperties kafkaTopicsProperties;

    public List<TaskDto> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskMapper::toDto)
                .toList();
    }

    public TaskDto getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new TaskNotFoundException("Task not found: " + id));
    }


    @Transactional
    public TaskDto createTask(CreateTaskRequest request) {
        Task task = new Task()
                .setTitle(request.title())
                .setDescription(request.description())
                .setStatus(TaskStatus.CREATED);

        if (request.userId() != null){
            User user = userRepository.findById(request.userId())
                    .orElseThrow(() -> new UserNotFoundException("User not found: " + request.userId()));
            task.setUser(user);
        }

        Task saved = taskRepository.save(task);

        TaskCreatedEvent event = new TaskCreatedEvent(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getStatus().name(),
                saved.getUser() != null ? saved.getUser().getId() : null,
                Instant.now()
        );

        outboxService.saveEvent(
                "TASK",
                String.valueOf(saved.getId()),
                "TASK_CREATED",
                kafkaTopicsProperties.getTaskCreated(),
                event
        );

        return taskMapper.toDto(saved);
    }


    @Transactional
    public TaskDto assignTask(Long taskId, AssignTaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found: " + taskId));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + request.userId()));

        task.setUser(user);
        Task saved = taskRepository.save(task);

        TaskAssignedEvent event = new TaskAssignedEvent(
                saved.getId(),
                user.getId(),
                Instant.now()
        );

        outboxService.saveEvent(
                "TASK",
                String.valueOf(saved.getId()),
                "TASK_ASSIGNED",
                kafkaTopicsProperties.getTaskAssigned(),
                event
        );

        return taskMapper.toDto(saved);
    }


    @Transactional
    public TaskDto changeStatus(Long taskId, ChangeTaskStatusRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found: " + taskId));

        task.setStatus(request.status());

        return taskMapper.toDto(taskRepository.save(task));
    }


}
