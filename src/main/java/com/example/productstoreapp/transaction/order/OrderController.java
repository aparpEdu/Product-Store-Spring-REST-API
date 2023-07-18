package com.example.productstoreapp.transaction.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "CRUD REST APIs for Order Resource")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @Operation(
            summary = "Place Order REST API",
            description = "Place Order REST API is used to insert order from user into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("users/{userId}/orders")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest, @PathVariable Long userId){
        return new ResponseEntity<>(orderService.placeOrder(orderRequest, userId), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Get All Orders REST API",
            description = "Get All Orders REST API is used to get all orders from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDetailsResponse>> getAllOrders(){
        return ResponseEntity.ok(orderService.checkAllOrders());
    }
    @Operation(
            summary = "Track Order REST API",
            description = "Track Order REST API is used to get order by its tracking number from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping("/orders/track")
    public ResponseEntity<OrderDetailsResponse> trackOrder(@RequestParam String trackingNumber){
        return  ResponseEntity.ok(orderService.trackOrder(trackingNumber));
    }
}
