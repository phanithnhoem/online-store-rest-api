package com.academy.onlinestore.api.user.web;

import jakarta.validation.constraints.NotNull;

public record IsDeletedDto(@NotNull Boolean isDeleted) {
}
