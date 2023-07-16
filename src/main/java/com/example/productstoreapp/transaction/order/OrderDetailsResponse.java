package com.example.productstoreapp.transaction.order;


import com.example.productstoreapp.product.ProductDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderDetailsResponse {
    private Long orderId;
    private String orderTrackingNumber;
    private int totalQuantity;
    private BigDecimal totalPrice;
    private String status;
    private List<ProductDto> products;
    private Long userId;
}
