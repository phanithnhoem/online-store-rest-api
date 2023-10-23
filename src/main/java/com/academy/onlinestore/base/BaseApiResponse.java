package com.academy.onlinestore.base;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record BaseApiResponse<T> (
                                  String message,
                                  HttpStatus status,
                                  Integer code,
                                  T data,
                                  LocalDateTime timestamp){
}
