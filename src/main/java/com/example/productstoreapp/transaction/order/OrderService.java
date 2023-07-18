package com.example.productstoreapp.transaction.order;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest orderRequest, Long userId);
    List<OrderDetailsResponse> checkAllOrders();
    OrderDetailsResponse trackOrder(String trackingNumber);
}
