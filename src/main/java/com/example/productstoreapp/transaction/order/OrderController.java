package com.example.productstoreapp.transaction.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){
        return new ResponseEntity<>(orderService.placeOrder(orderRequest), HttpStatus.CREATED);
    }
    @GetMapping("")
    public ResponseEntity<List<OrderDetailsResponse>> getAllOrders(){
        return ResponseEntity.ok(orderService.checkAllOrders());
    }
}
