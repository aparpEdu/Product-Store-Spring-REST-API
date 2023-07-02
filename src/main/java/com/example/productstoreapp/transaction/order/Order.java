package com.example.productstoreapp.transaction.order;

import com.example.productstoreapp.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private String orderTrackingNumber;
    private int totalQuantity;
    private BigDecimal totalPrice;
    private String status;

    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime lastUpdated;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> products = new HashSet<>();
    private Long shoppingCartId;

    @Override
    public int hashCode() {
        return Objects.hash(id, orderTrackingNumber, totalQuantity, totalPrice, status, dateCreated, lastUpdated, shoppingCartId);
    }

}
