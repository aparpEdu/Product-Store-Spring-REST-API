package com.example.productstoreapp.transaction.order;

import com.example.productstoreapp.transaction.payment.Payment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "OrderRequest Model Information")
public class OrderRequest {
    @Schema(description = "Store Order")
    private Order order;
    @Schema(description = "Order payment")
    private Payment payment;
    @Schema(description = "Products to order")
    private List<Long> productIds;
}
