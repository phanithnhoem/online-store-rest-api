    package com.academy.onlinestore.exception;

import com.academy.onlinestore.base.BaseErrorResponse;
import com.academy.onlinestore.base.BaseFieldError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @RestControllerAdvice Used to create global exception handlers for REST controllers
 */
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationException(MethodArgumentNotValidException ex){

        Map<String, Object> errorDto = new HashMap<>();
        List<BaseFieldError> errors = new ArrayList<>();
        ex.getFieldErrors().forEach(fieldError -> {
            errors.add(new BaseFieldError(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()));
        });
        errorDto.put("message", "Validation field, please check your input!");
        errorDto.put("status", false);
        errorDto.put("code", 1992);
        errorDto.put("timestamp", LocalDateTime.now());
        errorDto.put("errors", errors);
        return errorDto;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        BaseErrorResponse response = BaseErrorResponse.builder()
                .message(ex.getMessage())
                .code(1212)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errors(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        Map<String, Object> errorsDto = new HashMap<>();
        errorsDto.put("timestamp", LocalDateTime.now());
        errorsDto.put("status", false);
        errorsDto.put("message", ex.getMessage());
        errorsDto.put("code", 1233);
        return errorsDto;
    }
}
