package com.academy.onlinestore.api.user.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewUserDto(@NotBlank
                         String username,
                         @NotBlank
                         @Email
                         String email,
                         @NotBlank
                         String password,
                         @NotBlank
                         @Size(min = 3)
                         String nickName) {
}
