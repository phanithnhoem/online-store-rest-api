package com.academy.onlinestore.api.product;

import com.academy.onlinestore.api.product.web.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public void createNew(CategoryDto categoryDto) {
        Category category = categoryMapper.fromCategoryDto(categoryDto);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryMapper.toCategoryDtoToList(categoryList);
    }

    @Override
    public CategoryDto findByName(String name) {
        Category category = categoryRepository.findByName(name).orElseThrow();
        return categoryMapper.toCategoryDto(category);
    }
}
