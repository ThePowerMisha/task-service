package com.tpm.task_service.controller;

import com.tpm.task_service.dto.*;
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
    public ResponseDto<List<TaskDto>> getAllTasks(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseDto.success(taskService.getTasks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseDto<TaskDto> getDocumentById(@PathVariable Long id) {
        return ResponseDto.success(taskService.getTaskById(id));
    }

    @PutMapping("/create")
    public ResponseDto<TaskDto> createTask(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseDto.success(taskService.createTask(request));
    }

    @PatchMapping("/{id}/assign")
    public ResponseDto<TaskDto> assignTask(@PathVariable Long id,
                              @Valid @RequestBody AssignTaskRequest request) {
        return ResponseDto.success(taskService.assignTask(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseDto<TaskDto> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody ChangeTaskStatusRequest request
    ) {
        return ResponseDto.success(taskService.changeStatus(id, request));
    }
}
