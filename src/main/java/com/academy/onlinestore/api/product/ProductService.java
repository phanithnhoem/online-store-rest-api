package com.academy.onlinestore.api.product;

import com.academy.onlinestore.api.product.web.CreateProductDto;
import com.academy.onlinestore.api.product.web.ProductDto;
import com.academy.onlinestore.api.product.web.UpdateProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    void createNew(CreateProductDto createProductDto);
    void updateByUuid(String uuid, UpdateProductDto updateProductDto);
    void deleteByUuid(String uuid);
    List<ProductDto> findAllProducts();
    ProductDto findByUuid(String uuid);


    /**
     * Here is the practical area not related to daily learning
     */

    List<ProductDto> findAllProductsPageableTest(Pageable pageable);

}
