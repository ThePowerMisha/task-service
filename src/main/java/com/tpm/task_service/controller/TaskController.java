package com.tpm.task_service.controller;

import com.tpm.task_service.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    @GetMapping("/all")
    public List<TaskDto> getAllTask(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        return List.of();
    }

    @GetMapping("/{id}")
    public TaskDto getDocumentById(@PathVariable Long id){
        return new TaskDto(null,null,null,null,null);
    }


}
