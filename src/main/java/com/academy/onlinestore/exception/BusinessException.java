package com.academy.onlinestore.exception;

import com.academy.onlinestore.base.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class BusinessException {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleBusinessException(ResponseStatusException ex){
        var baseError = BaseErrorResponse.builder()
                .message("Something went wrong!")
                .code(7001)
                .status(HttpStatus.valueOf(ex.getStatusCode().toString()))
                .timestamp(LocalDateTime.now())
                .errors(ex.getReason())
                .build();
        return new ResponseEntity<>(baseError, ex.getStatusCode());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex){
        BaseErrorResponse baseError = BaseErrorResponse.builder()
                .message(ex.getMessage())
                .code(1111)
                .errors(ex.getStackTrace())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(baseError, HttpStatus.NOT_FOUND);
    }
}
