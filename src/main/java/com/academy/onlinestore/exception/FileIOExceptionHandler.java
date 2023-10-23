package com.academy.onlinestore.exception;

import com.academy.onlinestore.base.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class FileIOExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleFileIOException(IOException ex){
        BaseErrorResponse response = BaseErrorResponse.builder()
                .message(ex.getMessage())
                .code(5555)
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
