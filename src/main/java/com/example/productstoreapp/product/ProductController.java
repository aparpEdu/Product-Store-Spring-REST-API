package com.example.productstoreapp.product;

import com.example.productstoreapp.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }
    @GetMapping("{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        return  ResponseEntity.ok(productService.getProductById(productId));
    }

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

    @PutMapping("{productId}")
    public ResponseEntity<ProductDto> updateProductById(@Valid @RequestBody ProductDto productDto,
                                                        @PathVariable Long productId){
        return ResponseEntity.ok(productService.updateProductById(productDto, productId));
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long productId){
        productService.deleteProductById(productId);
        return ResponseEntity.ok("Product successfully deleted");
    }

}
