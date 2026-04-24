package com.tpm.task_service.service;

import com.tpm.task_service.dto.UserDto;
import com.tpm.task_service.exception.UserNotFoundException;
import com.tpm.task_service.mapper.UserMapper;
import com.tpm.task_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getUser(UUID uuid){
        return userMapper.toDto(
                userRepository.findById(uuid)
                        .orElseThrow(()->new UserNotFoundException("Can't find author with id: " + uuid)));
    }

    @Transactional
    public UserDto createUser(UserDto userDto){
        return userMapper.toDto(
                userRepository.save(
                        userMapper.toEntity(userDto))
        );
    }

}
