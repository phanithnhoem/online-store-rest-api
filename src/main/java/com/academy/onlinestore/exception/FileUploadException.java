package com.academy.onlinestore.exception;

import com.academy.onlinestore.base.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class FileUploadException {

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler
    public BaseErrorResponse<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        BaseErrorResponse response = BaseErrorResponse.builder()
                .message("Maximum upload size exceeded")
                .code(12000)
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .errors(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return response;
    }
}
