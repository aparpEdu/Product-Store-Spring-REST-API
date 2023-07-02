package com.example.productstoreapp.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@Schema(description = "ProductDto Model Information")
public class ProductDto {
    private Long id;
    @NotBlank(message = "Product SKU should not be left blank")
    @Schema(description = "Store product SKU")
    private String sku;
    @NotBlank(message = "Product name should not be left blank")
    @Size(min = 2, message = "Product name should be at least 2 symbols big")
    @Schema(description = "Store product's name")
    private String name;
    @Schema(description = "Store product's description")
    private String description;
    @Schema(description = "Store product availability")
    private boolean active;
    @Schema(description = "Store product's image URL")
    @URL
    private String imageUrl;
    private Long orderId;
}
