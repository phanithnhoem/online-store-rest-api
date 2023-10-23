package com.academy.onlinestore.api.product.web;

public record ProductDto(String uuid,
                         String code,
                         String name,
                         String slug,
                         String description,
                         String image,
                         String categoryName) {
}
