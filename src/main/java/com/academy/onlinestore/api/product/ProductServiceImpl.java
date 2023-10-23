package com.academy.onlinestore.api.product;

import com.academy.onlinestore.api.product.web.CreateProductDto;
import com.academy.onlinestore.api.product.web.UpdateProductDto;
import com.academy.onlinestore.api.product.web.ProductDto;
import com.academy.onlinestore.util.RandomUtil;
import com.academy.onlinestore.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    public void createNew(CreateProductDto createProductDto) {
        Product product = productMapper.fromCreateProductDto(createProductDto);
        product.setUuid(UUID.randomUUID().toString());
        product.setCode("PRO-" + RandomUtil.generateCode());
        product.setSlug(SlugUtil.toSlug(product.getName()));
        productRepository.save(product);
    }

    @Override
    public void updateByUuid(String uuid, UpdateProductDto updateProductDto) {
        // Step 1: Check uuid of product in the database
        if (productRepository.existsByUuid(uuid)){
            // Step 2: Load old product
            Product product = productRepository.findByUuid(uuid)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Product UUID = %s doesn't exist in database.", uuid)));
            // Step 3: Map updating product partially
            productMapper.fromUpdateProductDto(product, updateProductDto);

            if (updateProductDto != null){
                Category newCategory = new Category();
                newCategory.setId(updateProductDto.categoryId());
                product.setCategory(newCategory);
                product.setSlug(SlugUtil.toSlug(updateProductDto.name()));
            }
            // Step 4: Save latest product
            productRepository.save(product);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Product UUID = %s doesn't exist in database.", uuid));
    }

    @Override
    public void deleteByUuid(String uuid) {
        Product product = productRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Product UUID = %s doesn't exist in database.", uuid))
        );
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toProductDtoList(products);
    }

    @Override
    public ProductDto findByUuid(String uuid) {
        Product product = productRepository.findByUuid(uuid).orElseThrow();
        ProductDto productDto = productMapper.toProductDto(product);
        return productDto;
    }

    /**
     * This block just for testing only
     * @param pageable
     * @return
     */

    @Override
    public List<ProductDto> findAllProductsPageableTest(Pageable pageable) {
        List<Product> products = productRepository.findAll(pageable).get().toList();
        List<ProductDto> productDtoList = productMapper.toProductDtoList(products);
        return productDtoList;
    }
}
