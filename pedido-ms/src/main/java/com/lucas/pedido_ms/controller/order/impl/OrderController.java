package com.lucas.pedido_ms.controller.order.impl;

import com.lucas.pedido_ms.controller.order.IOrderController;
import com.lucas.pedido_ms.domains.order.Order;
import com.lucas.pedido_ms.domains.order.dto.CreateOrderDto;
import com.lucas.pedido_ms.domains.order.dto.DeleteOrderDto;
import com.lucas.pedido_ms.domains.order.dto.UpdateOrderDto;
import com.lucas.pedido_ms.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/orders")
public class OrderController implements IOrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody CreateOrderDto data){
        var order = orderService.create(data);

        return ResponseEntity.status(201).body(order);
    }

    @PutMapping
    public ResponseEntity<Order> update(@RequestBody UpdateOrderDto data){
        var order = orderService.update(data);

        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody DeleteOrderDto data){
        orderService.delete(data);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Order>> get(){
        var orders = orderService.get();

        return ResponseEntity.ok().body(orders);
    }
}
