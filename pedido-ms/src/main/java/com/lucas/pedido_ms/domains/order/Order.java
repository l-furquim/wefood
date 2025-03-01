package com.lucas.pedido_ms.domains.order;

import com.lucas.pedido_ms.domains.order.enums.OrderStatus;
import com.lucas.pedido_ms.domains.orderitem.OrderItem;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Table(name = "orders")
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
    private int orderItems;

    private OrderStatus status;

    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="order", fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();

    private String address;


    public Order(){

    }

    public Order(BigDecimal total, String userId, OrderStatus status, List<OrderItem> items, String address) {
        this.total = total;
        this.userId = userId;
        this.orderItems = items.size();
        this.status = status;
        this.items = items;
        this.address = address;
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

    public void setItems(int orderItems) {
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

    public int getItems() {
        return orderItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void addItem(OrderItem item, Long newQuantity){
        this.items.add(item);
        this.orderItems += newQuantity;
    }
    public void removeItem(OrderItem item, Long newQuantity){
        this.items.remove(item);
        this.orderItems -= newQuantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void calculatePrice(BigDecimal newPrice,Long newQuantity){
        this.total = this.total.add(
                newPrice.multiply(
                        BigDecimal.valueOf(newQuantity))
        );
    }
}
