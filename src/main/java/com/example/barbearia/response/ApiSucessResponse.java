package com.example.barbearia.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSucessResponse<T> {
    private LocalDateTime timestamp;
    private boolean success;
    private String message;
    private T data;

    public ApiSucessResponse(boolean success, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.success = success;
        this.message = message;
        this.data = data;
    }


    // Ideal para GET(Devolver apenas objeto)
    public static <T> ApiSucessResponse<T> success(T data) {
        return new ApiSucessResponse<>(true, null, data);
    }

    // Ideal para usar em POST e PUT(Devolver mensagem de retorno ao usuário e objeto)
    public static <T> ApiSucessResponse<T> success(String message, T data) {
        return new ApiSucessResponse<>(true, message, data);
    }

    // Ideal para DELETE(Devolver apenas mensagem de retorno ao usuário)
    public static <T> ApiSucessResponse<T> success(String message) {
        return new ApiSucessResponse<>(true, message, null);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
