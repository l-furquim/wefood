package com.lucas.pedido_ms.domains.order;

import com.lucas.pedido_ms.domains.order.enums.OrderStatus;
import com.lucas.pedido_ms.domains.orderitem.OrderItem;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "order_items")
    private Long orderItems;

    private OrderStatus status;

    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="order")
    private List<OrderItem> items = new ArrayList<>();


    public Order(){

    }

    public Order(BigDecimal total, String userId, Long orderItems, OrderStatus status, List<OrderItem> items) {
        this.total = total;
        this.userId = userId;
        this.orderItems = orderItems;
        this.status = status;
        this.items = items;
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

    public void setItems(Long orderItems) {
        this.orderItems = orderItems;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void removeItem(int orderItems){
        this.orderItems -= orderItems;
    }

    public void addItem(int orderItems){
        this.orderItems += orderItems;
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
        return orderItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void addItem(OrderItem item){
        this.items.add(item);
    }
    public void removeItem(OrderItem item){
        this.items.remove(item);
    }
}
