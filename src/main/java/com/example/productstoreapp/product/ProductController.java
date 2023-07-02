package com.example.productstoreapp.product;

import com.example.productstoreapp.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "CRUD REST APIs for Product Resource")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Create Product REST API",
            description = "Create Product REST API is used to save a product into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Get Product by Id REST API",
            description = "Get Product by Id REST API is used to get a single product from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping("{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        return  ResponseEntity.ok(productService.getProductById(productId));
    }
    @Operation(
            summary = "Get All Products REST API",
            description = "Get All Products REST API is used to get all products from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping()
    public ResponseEntity<ProductResponse> getAllProducts
            (
                    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                            required = false) int pageNo,
                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                            required = false) int pageSize,
                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                            required = false) String sortBy,
                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                            required = false) String sortDir
            ){
        return ResponseEntity.ok(productService.getAllProducts(pageNo, pageSize, sortBy, sortDir));
    }
    @Operation(
            summary = "Search Products by Name or Description query REST API",
            description = "Search Products by Name or Description query REST API is used to query all products"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping("/search")
    public ResponseEntity<ProductResponse> searchProducts
            (
                    @RequestParam("query")  String query,
                    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                            required = false) int pageNo,
                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                            required = false) int pageSize,
                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                            required = false) String sortBy,
                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                            required = false) String sortDir
            ){
        return ResponseEntity.ok(productService.searchProducts(query, pageNo, pageSize, sortBy, sortDir));
    }
    @Operation(
            summary = "Update Product by Id REST API",
            description = "Update Product by Id REST API is used to update a single product from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @PutMapping("{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProductById(@Valid @RequestBody ProductDto productDto,
                                                        @PathVariable Long productId){
        return ResponseEntity.ok(productService.updateProductById(productDto, productId));
    }
    @Operation(
            summary = "Delete Product by Id REST API",
            description = "Delete Product by Id REST API is used to delete a single product from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @DeleteMapping("{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProductById(@PathVariable Long productId){
        productService.deleteProductById(productId);
        return ResponseEntity.ok("Product successfully deleted");
    }

}
