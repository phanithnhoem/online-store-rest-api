package com.academy.onlinestore.api.product;

import com.academy.onlinestore.api.product.web.CreateProductDto;
import com.academy.onlinestore.api.product.web.UpdateProductDto;
import com.academy.onlinestore.base.BaseApiResponse;
import com.academy.onlinestore.api.product.web.ProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteProductByUuid(@PathVariable String uuid){
        productService.deleteByUuid(uuid);

    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{uuid}")
    public void updateProductByUuid(@PathVariable String uuid, @RequestBody UpdateProductDto productDto){
        productService.updateByUuid(uuid, productDto);
    }


    // @Valid implement validation that we defined on ProductDto
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNewProduct(@RequestBody @Valid CreateProductDto createProductDto){
        System.out.println(createProductDto);
        productService.createNew(createProductDto);
    }

    @GetMapping
    public List<ProductDto> getAllProducts(){
        return productService.findAllProducts();
    }


    /**
     * Noted: This block just testing area or practical area
     */

    @PostMapping("/test")
    public ResponseEntity<BaseApiResponse<ProductDto>> createNewProductTest(@RequestBody @Valid CreateProductDto createProductDto){
        BaseApiResponse response = BaseApiResponse.builder()
                .message("New product created successfully.")
                .status(HttpStatus.CREATED)
                .code(1201)
                .data(createProductDto)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/test/{uuid}")
    public ResponseEntity<BaseApiResponse<ProductDto>> loadProductByUuidTest(@PathVariable String uuid){
        ProductDto productDto = productService.findByUuid(uuid);
        BaseApiResponse response = BaseApiResponse.builder()
                .message(String.format("Product with UUID = %s founded.", uuid))
                .status(HttpStatus.OK)
                .code(10000)
                .data(productDto)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public ResponseEntity<BaseApiResponse<List<ProductDto>>> loadAllProductsTest(){
        List<ProductDto> products = productService.findAllProducts();
        BaseApiResponse response = BaseApiResponse.builder()
                .message("Load all products from database successfully.")
                .status(HttpStatus.OK)
                .code(12000)
                .data(products)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/test/{pageNo}/{recordCount}")
    public ResponseEntity<BaseApiResponse<List<ProductDto>>> loadProductsPagination(@PathVariable Integer pageNo,
                                                                                    @PathVariable Integer recordCount){
        Pageable pageable = PageRequest.of(pageNo, recordCount, Sort.by("name"));
        List<ProductDto> products = productService.findAllProductsPageableTest(pageable);
        BaseApiResponse response = BaseApiResponse.builder()
                .message("Load all products with pagination successfully.")
                .status(HttpStatus.OK)
                .code(12000)
                .data(products)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }
}