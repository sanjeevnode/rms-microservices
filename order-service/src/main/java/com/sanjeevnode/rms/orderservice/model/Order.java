package com.sanjeevnode.rms.orderservice.model;

import com.sanjeevnode.rms.orderservice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status=OrderStatus.PENDING;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public String getCreatedAt() {
        if (createdAt == null) return null;
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getUpdatedAt() {
        if (updatedAt == null) return null;
        return updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getOrderDate() {
        if (orderDate == null) return null;
        return orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
