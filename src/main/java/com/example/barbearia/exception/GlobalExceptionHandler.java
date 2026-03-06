package com.example.barbearia.exception;

import com.example.barbearia.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(EntidadeNaoEncontradaException.class)
        public ResponseEntity<ErrorResponseDto> handleNaoEncontrado(EntidadeNaoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponseDto(404, "Não encontrado", ex.getMessage(), LocalDateTime.now())
            );
        }

        @ExceptionHandler(HorarioIndisponivelException.class)
        public ResponseEntity<ErrorResponseDto> handleHorarioIndisponivel(HorarioIndisponivelException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorResponseDto(409, "Horário indisponível", ex.getMessage(), LocalDateTime.now())
            );
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponseDto> handleArgumentoInvalido(IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponseDto(400, "Requisição inválida", ex.getMessage(),  LocalDateTime.now())
            );
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
            String mensagem = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponseDto(400, "Erro de validação", mensagem, LocalDateTime.now())
            );
        }




}
