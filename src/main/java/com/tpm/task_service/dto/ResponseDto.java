package com.tpm.task_service.dto;

public record ResponseDto<T>(Boolean success, T result, ExceptionDto error) {
    public static <T> ResponseDto<T> success(T result) {
        return new ResponseDto<>(true, result, null);
    }

    public static ResponseDto<Void> error(String code, String message) {
        return new ResponseDto<>(false, null, new ExceptionDto(code, message));
    }
}
