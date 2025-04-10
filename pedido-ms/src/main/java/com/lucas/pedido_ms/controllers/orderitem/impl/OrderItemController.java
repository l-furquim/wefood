package com.lucas.pedido_ms.controllers.orderitem.impl;

import com.lucas.pedido_ms.controllers.orderitem.IOrderItemController;
import com.lucas.pedido_ms.domains.orderitem.OrderItem;
import com.lucas.pedido_ms.domains.orderitem.dto.*;
import com.lucas.pedido_ms.services.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/api/orderitems")
public class OrderItemController  implements IOrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<OrderItem> create(
            @RequestPart("orderItemMetaData") CreateOrderItemMetadataDto data,
            @RequestPart("orderItemImage") MultipartFile image
            ){
        var orderItem = orderItemService.create(new CreateOrderItemDto(
                data,
                image
        ));

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
    public ResponseEntity<List<FindByRestaurantDto>> getByRestaurantId(@PathVariable("restaurantId") Long restaurantId){
        var restaurants = this.orderItemService.findByRestaurant(restaurantId);

        return ResponseEntity.ok().body(restaurants);
    }
}
