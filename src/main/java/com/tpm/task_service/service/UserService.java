package com.tpm.task_service.service;

import com.tpm.task_service.dto.CreateUserRequest;
import com.tpm.task_service.dto.UserDto;
import com.tpm.task_service.entity.User;
import com.tpm.task_service.exception.EmailAlreadyExistException;
import com.tpm.task_service.exception.UserNotFoundException;
import com.tpm.task_service.mapper.UserMapper;
import com.tpm.task_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getUser(UUID uuid){
        return userRepository.findById(uuid)
                .map(userMapper::toDto)
                .orElseThrow(()->new UserNotFoundException("Can't find user with id: " + uuid));
    }

    public List<UserDto> getUsers(Pageable pageable){
        return userRepository.findAll(pageable)
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional
    public UserDto createUser(CreateUserRequest request){

        if(userRepository.existsByEmail(request.email().toLowerCase())){
            throw new EmailAlreadyExistException("This email has already taken");
        }

        User user = new User()
                .setName(request.name())
                .setEmail(request.email().toLowerCase());

        return userMapper.toDto(
                userRepository.save(user)
        );
    }

}
