package com.academy.onlinestore.api.product;


import com.academy.onlinestore.api.product.web.CreateProductDto;
import com.academy.onlinestore.api.product.web.UpdateProductDto;
import com.academy.onlinestore.api.product.web.ProductDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "categoryId", target = "category.id")
    Product fromCreateProductDto(CreateProductDto createProductDto);

    CreateProductDto toCreateProductDto(Product product);

//    @Mapping(source = "categoryId", target = "category.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdateProductDto(@MappingTarget Product product, UpdateProductDto updateProductDto);

    UpdateProductDto toUpdateProductDto(Product product);

    @Mapping(source = "category.name", target = "categoryName")
    ProductDto toProductDto(Product product);

    List<ProductDto> toProductDtoList(List<Product> products);

    /**
     * This area just for testing only
     */

    ProductDto fromCreateProductDtoTest(CreateProductDto createProductDto);
}
