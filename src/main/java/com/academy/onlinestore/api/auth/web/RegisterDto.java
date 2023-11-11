package com.academy.onlinestore.api.auth.web;

import jakarta.validation.constraints.*;

import java.util.Set;

public record RegisterDto(@NotBlank
                          String username,
                          @NotBlank
                          @Email
                          String email,
                          @NotBlank
                          String password,
                          @NotBlank
                          @Size(min = 4)
                          String nickName,
                          @NotNull
                          @Size(min = 1)
                          Set<@Positive Integer> roleIds) {
}
