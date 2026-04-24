package com.tpm.task_service.controller;

import com.tpm.task_service.dto.ResponseDto;
import com.tpm.task_service.exception.EmailAlreadyExistException;
import com.tpm.task_service.exception.TaskNotFoundException;
import com.tpm.task_service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body(ResponseDto.error("IllegalArgumentException",ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDto<Void>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity
                .badRequest()
                .body(ResponseDto.error("UserNotFoundException", ex.getMessage()));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDto<Void>> handleTaskNotFound(TaskNotFoundException ex) {
        return ResponseEntity
                .badRequest()
                .body(ResponseDto.error("TaskNotFoundException", ex.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ResponseDto<Void>> handleEmailExist(EmailAlreadyExistException ex) {
        return ResponseEntity
                .badRequest()
                .body(ResponseDto.error("EmailAlreadyExistException", ex.getMessage()));
    }

}
