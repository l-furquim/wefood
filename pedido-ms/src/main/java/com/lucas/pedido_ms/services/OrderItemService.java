package com.lucas.pedido_ms.services;


import com.lucas.pedido_ms.domains.orderitem.OrderItem;
import com.lucas.pedido_ms.domains.orderitem.dto.CreateOrderItemDto;
import com.lucas.pedido_ms.domains.order.exception.InvalidOrderCreationException;
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

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem create(CreateOrderItemDto data){
        if(data.title().isBlank() || data.description().isEmpty() ||
            data.price().compareTo(BigDecimal.ZERO) < 0 || data.quantity() <= 0){
            throw new InvalidOrderCreationException();
        }

        var orderItem = new OrderItem(
            data.title(),
                data.description(),
                data.quantity(),
                data.price()
        );

        if(data.order() != null){
            if(!data.order().getUserId().equals(data.userId())){
                throw new InvalidOrderCreationException();
            }
            orderItem.setOrder(data.order());
        }

        return orderItem;
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
}
