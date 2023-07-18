package com.example.productstoreapp.transaction.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "OrderResponse Information")
public class OrderResponse {
    @Schema(description = "Store order tracking number")
    private String orderTrackingNumber;
    @Schema(description = "Store order status")
    private String status;
    @Schema(description = "Store order feedback message")
    private String message;
}
