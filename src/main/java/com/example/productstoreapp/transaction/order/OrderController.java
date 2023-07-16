package com.example.productstoreapp.transaction.order;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("users/{userId}/orders")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest, @PathVariable Long userId){
        return new ResponseEntity<>(orderService.placeOrder(orderRequest, userId), HttpStatus.CREATED);
    }
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDetailsResponse>> getAllOrders(){
        return ResponseEntity.ok(orderService.checkAllOrders());
    }
}
