package com.example.productstoreapp.product;

import com.example.productstoreapp.transaction.order.Order;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String sku;
    @Column(nullable = false)
    private String name;
    private String description;
    private boolean active;
    private String imageUrl;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public int hashCode() {
        return Objects.hash(id, sku, name, description, active, imageUrl, dateCreated, dateUpdated);
    }
}
