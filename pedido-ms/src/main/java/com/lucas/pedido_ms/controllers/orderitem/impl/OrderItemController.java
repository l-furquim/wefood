package com.lucas.pedido_ms.controllers.orderitem.impl;

import com.lucas.pedido_ms.controllers.orderitem.IOrderItemController;
import com.lucas.pedido_ms.domains.orderitem.OrderItem;
import com.lucas.pedido_ms.domains.orderitem.dto.CreateOrderItemDto;
import com.lucas.pedido_ms.domains.orderitem.dto.DeleteOrderItemDto;
import com.lucas.pedido_ms.domains.orderitem.dto.UpdateOrderItemDto;
import com.lucas.pedido_ms.services.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/orderitems")
public class OrderItemController  implements IOrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public ResponseEntity<OrderItem> create(@RequestBody CreateOrderItemDto data){
        var orderItem = orderItemService.create(data);

        return ResponseEntity.status(201).body(orderItem);
    }

    @PutMapping
    public ResponseEntity<OrderItem> update(@RequestBody UpdateOrderItemDto data){
        var orderItem = orderItemService.update(data);

        return ResponseEntity.ok().body(orderItem);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody DeleteOrderItemDto data){
        orderItemService.delete(data);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> get(){
        return ResponseEntity.ok().body(orderItemService.get());
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<OrderItem>> getByRestaurantId(@PathVariable("restaurantId") Long restaurantId){
        var restaurants = this.orderItemService.findByRestaurant(restaurantId);

        return ResponseEntity.ok().body(restaurants);
    }
}
