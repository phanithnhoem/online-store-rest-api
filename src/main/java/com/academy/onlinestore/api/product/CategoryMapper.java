package com.academy.onlinestore.api.product;

import com.academy.onlinestore.api.product.web.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category fromCategoryDto(CategoryDto categoryDto);
    CategoryDto toCategoryDto(Category category);

    List<CategoryDto> toCategoryDtoToList(List<Category> categories);


}
