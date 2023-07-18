package com.example.productstoreapp.transaction.order;


import com.example.productstoreapp.product.ProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Schema(description = "OrderDetailsResponse Model Information")
public class OrderDetailsResponse {
    private Long orderId;
    @Schema(description = "Store order tracking number")
    private String orderTrackingNumber;
    @Schema(description = "Store order products quantity")
    private int totalQuantity;
    @Schema(description = "Store order products total price")
    private BigDecimal totalPrice;
    @Schema(description = "Store order status")
    private String status;
    @Schema(description = "Store order products")
    private List<ProductDto> products;
    @Schema(description = "User which ordered")
    private Long userId;
}
