package com.academy.onlinestore.base;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record BaseErrorResponse<T>(String message,
                                   Integer code,
                                   HttpStatus status,
                                   T errors,
                                   LocalDateTime timestamp) {
}
