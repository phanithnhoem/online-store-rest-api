package com.academy.onlinestore.api.product;

import com.academy.onlinestore.api.product.web.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public void createNewCategory(@RequestBody CategoryDto categoryDto){
        categoryService.createNew(categoryDto);
    }

    @GetMapping("/{name}")
    public CategoryDto getCategoryByName(@PathVariable String name){
        return categoryService.findByName(name);
    }

    @GetMapping
    List<CategoryDto> getAllCategories(){
        return categoryService.findAll();
    }

}
