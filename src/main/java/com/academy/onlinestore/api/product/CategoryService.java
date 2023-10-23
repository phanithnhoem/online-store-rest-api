package com.academy.onlinestore.api.product;

import com.academy.onlinestore.api.product.web.CategoryDto;

import java.util.List;

public interface CategoryService {

    // This method is used to create new Category resource into database
    void createNew(CategoryDto categoryDto);

    /**
     * This method is used to retrieve resource category from database
     * @return List<CategoryDto>
     */
    List<CategoryDto> findAll();

    /**
     * This method used to retrieve resource by name
     * @param name
     * @return
     */
    CategoryDto findByName(String name);
}
