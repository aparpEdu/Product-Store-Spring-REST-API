package com.example.productstoreapp.transaction.order;

import com.example.productstoreapp.transaction.payment.Payment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private Order order;
    private Payment payment;
    private List<Long> productIds;
}
