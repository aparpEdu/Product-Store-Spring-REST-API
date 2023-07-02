package com.example.productstoreapp.transaction.payment;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    private String type;
    private String cardName;
    private String cardNumber;
    private int expiryYear;
    private int expiryMonth;
    private int cvc;
    private Long orderId;
}
