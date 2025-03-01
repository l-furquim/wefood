package com.lucas.pedido_ms.domains.orderitem;

import com.lucas.pedido_ms.domains.order.Order;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Table(name = "orders_items")
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Long quantity;

    private BigDecimal price;

    @Nullable
    @ManyToOne
    private Order order;


    public OrderItem(){

    }

    public OrderItem(String title, String description, Long quantity, BigDecimal price) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
