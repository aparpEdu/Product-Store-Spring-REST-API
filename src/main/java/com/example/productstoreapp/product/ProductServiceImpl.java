package com.example.productstoreapp.product;

import com.example.productstoreapp.exception.ResourceNotFoundException;
import com.example.productstoreapp.utils.CustomMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CustomMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, CustomMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product productToCreate = mapToProduct(productDto);
        return mapToDto(productRepository.save(productToCreate));
    }

    @Override
    public ProductResponse searchProducts(String query, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sortDirection = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection);
        return getProductResponse(productRepository.searchProducts(query, pageable));
    }

    @Override
    public ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sortDirection = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection);
        return getProductResponse(productRepository.findAll(pageable));
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        return mapToDto(foundProduct);
    }

    @Override
    public ProductDto updateProductById(ProductDto productDto, Long productId) {
        Product productToUpdate = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
       productToUpdate.setActive(productDto.isActive());
       productToUpdate.setDescription(productDto.getDescription());
       productToUpdate.setName(productDto.getName());
       productToUpdate.setSku(productDto.getSku());
       productToUpdate.setImageUrl(productDto.getImageUrl());
        return mapToDto(productRepository.save(productToUpdate));
    }

    @Override
    public void deleteProductById(Long productId) {
        Product productForDeletion = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        productRepository.delete(productForDeletion);
    }
    private ProductResponse getProductResponse(Page<Product> products) {
        List<Product> listOfProducts = products.getContent();
        List<ProductDto> content = listOfProducts.stream().map(this::mapToDto).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setLast(products.isLast());
        productResponse.setTotalPages(products.getTotalPages());
        return productResponse;
    }
    private ProductDto mapToDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }
    private Product mapToProduct(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }
}
