package com.tpm.task_service.controller;

import com.tpm.task_service.dto.AssignTaskRequest;
import com.tpm.task_service.dto.ChangeTaskStatusRequest;
import com.tpm.task_service.dto.CreateTaskRequest;
import com.tpm.task_service.dto.TaskDto;
import com.tpm.task_service.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/all")
    public Page<TaskDto> getAllTasks(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return taskService.getTasks(pageable);
    }

    @GetMapping("/{id}")
    public TaskDto getDocumentById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/create")
    public TaskDto createTask(@Valid @RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @PatchMapping("/{id}/assign")
    public TaskDto assignTask(@PathVariable Long id,
                              @Valid @RequestBody AssignTaskRequest request) {
        return taskService.assignTask(id, request);
    }

    @PatchMapping("/{id}/status")
    public TaskDto changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody ChangeTaskStatusRequest request
    ) {
        return taskService.changeStatus(id, request);
    }
}
