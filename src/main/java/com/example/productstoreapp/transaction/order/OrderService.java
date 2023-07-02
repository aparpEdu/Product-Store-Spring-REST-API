package com.example.productstoreapp.transaction.order;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);
    List<OrderDetailsResponse> checkAllOrders();
}
