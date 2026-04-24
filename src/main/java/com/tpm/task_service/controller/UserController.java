package com.tpm.task_service.controller;

import com.tpm.task_service.dto.CreateUserRequest;
import com.tpm.task_service.dto.UserDto;
import com.tpm.task_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService  userService;

    @GetMapping
    public Page<UserDto> getUsers(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        return userService.getUsers(pageable);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable UUID uuid){
        return userService.getUser(uuid);
    }

    @PutMapping
    public UserDto createUser(@Valid @RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }
}
