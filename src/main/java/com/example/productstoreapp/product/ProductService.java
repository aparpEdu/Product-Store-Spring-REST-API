package com.example.productstoreapp.product;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductResponse searchProducts(String query, int pageNo, int pageSize, String sortBy, String sortDir);
    ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir);
    ProductDto getProductById(Long id);
    ProductDto updateProductById(ProductDto productDto, Long productId);
    void deleteProductById(Long productId);
}
