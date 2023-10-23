package com.academy.onlinestore.api.product.web;

import jakarta.validation.constraints.*;

public record UpdateProductDto(
                               String name,
                               String description,
                               @Positive
                               Integer categoryId) {
}
