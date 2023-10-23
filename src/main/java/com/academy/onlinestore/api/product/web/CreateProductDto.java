package com.academy.onlinestore.api.product.web;

import jakarta.validation.constraints.*;

public record CreateProductDto(@NotBlank(message = "Name is required.")
                               @Size(min = 5, max = 255)
                               String name,
                               @NotBlank(message = "Description is required.")
                               @Size(min = 5, message = "Description must greater than 5 digits.")
                               String description,
                               @NotBlank
                               String image,
                               @NotNull(message = "Category Id is required.")
                               @Positive
                               Integer categoryId) {
    // We can custom via message bundle.
}
