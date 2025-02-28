package com.lucas.pedido_ms.services;

import com.lucas.pedido_ms.domains.order.Order;
import com.lucas.pedido_ms.domains.order.dto.AddressDto;
import com.lucas.pedido_ms.domains.order.dto.CreateOrderDto;
import com.lucas.pedido_ms.domains.order.dto.DeleteOrderDto;
import com.lucas.pedido_ms.domains.order.dto.UpdateOrderDto;
import com.lucas.pedido_ms.domains.order.enums.OrderStatus;
import com.lucas.pedido_ms.domains.order.exception.InvalidOrderCreationException;
import com.lucas.pedido_ms.domains.order.exception.InvalidOrderUpdateException;
import com.lucas.pedido_ms.domains.order.exception.OrderNotFoundException;
import com.lucas.pedido_ms.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order create(CreateOrderDto data){
        if(data.userId() == null || data.userId().isEmpty() || data.items() == null){
            throw new InvalidOrderCreationException();
        }

        var totalPrice = BigDecimal.ZERO;

        data.items().forEach(
                item -> totalPrice.add(item.getPrice())
        );

        var addressFormated = formatAddress(data.address());

        var order = new Order(
                totalPrice,
                data.userId(),
                data.items().size(),
                OrderStatus.PREPARING,
                data.items(),
                addressFormated
        );
        orderRepository.save(order);

        return order;
    }

    @Transactional
    public Order update(UpdateOrderDto data){
        if(data.address() == null || data.id() == null){
            throw new InvalidOrderUpdateException("Invalid data for updating the order");
        }
        var order = orderRepository.findById(data.id());

        if(order.isEmpty()){
            throw new OrderNotFoundException("Order cannot be found");
        }

        order.get().setAddress(formatAddress(data.address()));

        orderRepository.save(order.get());


        return order.get();
    }
    @Transactional
    public void delete(DeleteOrderDto data) {
        var order = orderRepository.findById(data.id())
                        .orElseThrow(() -> new OrderNotFoundException("Cannot found order"));

        orderRepository.delete(order);
    }

    public List<Order> get(){
        return orderRepository.findAll();
    }


    private String formatAddress(AddressDto address){
        return "%s, %s - %s/%s".
                formatted(address.street(), address.number(), address.zip(), address.state());
    }

}
