package com.lucas.pedido_ms.domains;

import com.lucas.pedido_ms.domains.enums.OrderStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "products")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal total;

    @Column(name = "user_id")
    private String userId;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private Long items;

    private OrderStatus status;

    public Order(){

    }

    public Order(BigDecimal total, String userId, Long items, OrderStatus status) {
        this.total = total;
        this.userId = userId;
        this.items = items;
        this.status = status;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setItems(Long items) {
        this.items = items;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void removeItem(int item){
        this.items -= item;
    }

    public void addItem(int item){
        this.items += item;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
