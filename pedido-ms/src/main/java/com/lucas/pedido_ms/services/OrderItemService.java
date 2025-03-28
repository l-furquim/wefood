package com.lucas.pedido_ms.services;

import com.lucas.pedido_ms.domains.orderitem.OrderItem;
import com.lucas.pedido_ms.domains.orderitem.dto.CreateOrderItemDto;
import com.lucas.pedido_ms.domains.orderitem.dto.DeleteOrderItemDto;
import com.lucas.pedido_ms.domains.orderitem.dto.UpdateOrderItemDto;
import com.lucas.pedido_ms.domains.orderitem.exception.InvalidOrderItemDataException;
import com.lucas.pedido_ms.domains.orderitem.exception.OrderItemNotFoundException;
import com.lucas.pedido_ms.repositories.OrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderItemService {

    private static final Logger log = LoggerFactory.getLogger(OrderItemService.class);
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final IRestaurantService restaurantService;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderService orderService, IRestaurantService restaurantService) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.restaurantService = restaurantService;
    }

    public OrderItem create(CreateOrderItemDto data){
        if(data.title() == null || data.title().isEmpty() || data.description() == null || data.description().isEmpty() ||
            data.price().compareTo(BigDecimal.ZERO) < 0 || data.quantity() <= 0 || data.userId() == null || data.userId().isEmpty()){
            throw new InvalidOrderItemDataException("Error creating the order item: invalid data");
        }

        var restaurant = restaurantService.getById(data.restaurantId());

        if(restaurant == null){
            throw new OrderItemNotFoundException("Could not found the restaurant for the order item creation");
        }

        var orderItem = new OrderItem(
                data.title(),
                data.description(),
                data.quantity(),
                data.price(),
                data.restaurantId()
        );

//        if(data.orderId() != null){
//            var order = orderService.findById(data.orderId());
//            if(order.getUserId().equals(data.userId())){
//                throw new InvalidOrderItemDataException("Error creating the order item: cannot create a order that doesn't contains the user id");
//            }
//
//            orderItem.setOrder(order);
//        }

        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public OrderItem update(UpdateOrderItemDto data){
        if(data.id() == null){
            throw new InvalidOrderItemDataException("Order item id is null");
        }

        var orderItem = orderItemRepository.findById(data.id());

        if(orderItem.isEmpty()){
            throw new OrderItemNotFoundException("Order not found");
        }

        if(data.title() != null) orderItem.get().setTitle(data.title());
        if(data.description() != null) orderItem.get().setDescription(data.description());
        if(data.price() != null) orderItem.get().setPrice(data.price());
        if(data.quantity() != null) orderItem.get().setQuantity(data.quantity());
        if(data.order() != null) orderItem.get().setOrder(data.order());

        orderItemRepository.save(orderItem.get());

        return orderItem.get();
    }

    @Transactional
    public void delete(DeleteOrderItemDto data){
        var orderItem = orderItemRepository.findById(data.id())
                .orElseThrow(() -> new OrderItemNotFoundException("Order item not found for delete"));

        orderItemRepository.delete(orderItem);
    }

    public List<OrderItem> get(){
        return orderItemRepository.findAll();
    }

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new OrderItemNotFoundException("Cannot found the order item by id"));
    }
    
    public List<OrderItem> findByRestaurant(Long restaurantId){
        return this.orderItemRepository.findByRestaurantId(restaurantId);
    }
}
