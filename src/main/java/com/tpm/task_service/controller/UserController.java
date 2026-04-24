package com.tpm.task_service.controller;

import com.tpm.task_service.dto.CreateUserRequest;
import com.tpm.task_service.dto.ResponseDto;
import com.tpm.task_service.dto.UserDto;
import com.tpm.task_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService  userService;

    @GetMapping
    public ResponseDto<List<UserDto>> getUsers(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        return ResponseDto.success(userService.getUsers(pageable));
    }

    @GetMapping("/{id}/")
    public ResponseDto<UserDto> getUserById(@PathVariable UUID id){
        return ResponseDto.success(userService.getUser(id));
    }

    @PutMapping
    public ResponseDto<UserDto> createUser(@Valid @RequestBody CreateUserRequest request){
        return ResponseDto.success(userService.createUser(request));
    }
}
