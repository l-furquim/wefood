package com.lucas.pedido_ms.services;

import com.lucas.pedido_ms.domains.order.Order;
import com.lucas.pedido_ms.domains.order.dto.*;
import com.lucas.pedido_ms.domains.order.enums.OrderStatus;
import com.lucas.pedido_ms.domains.order.exception.InvalidOrderCreationException;
import com.lucas.pedido_ms.domains.order.exception.InvalidOrderUpdateException;
import com.lucas.pedido_ms.domains.order.exception.OrderNotFoundException;
import com.lucas.pedido_ms.domains.orderitem.exception.OrderItemNotFoundException;
import com.lucas.pedido_ms.repositories.OrderItemRepository;
import com.lucas.pedido_ms.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final KafkaTemplate<String, SendOrderPaymentRequestDto> transactionTemplate;
    private final KafkaTemplate<String, SendOrderNotificationDto> notificationTemplate;
    private static final String TOPIC = "transaction.request";
    private static final String RESTAURANT_ID = "oijqwoieqwje";



    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        KafkaTemplate<String, SendOrderPaymentRequestDto> transactionTemplate,
                        KafkaTemplate<String, SendOrderNotificationDto> notificationTemplate
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.transactionTemplate = transactionTemplate;
        this.notificationTemplate = notificationTemplate;
    }

    public Order create(CreateOrderDto data){
        if(data.userId() == null || data.userId().isEmpty() || data.items() == null){
            throw new InvalidOrderCreationException("Error creating the order: invalid data");
        }

        var totalPrice = data.items().stream()
                .map(item ->item.price())
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        var orderItems = data.items().
                stream()
                .map(item -> orderItemRepository.findById(item.id())
                        .orElseThrow(() -> new OrderItemNotFoundException("Cannot found the order item for the order creation")))
                .toList();

        var addressFormated = formatAddress(data.address());

        var order = new Order(
                totalPrice,
                data.userId(),
                OrderStatus.WAITING_PAYMENT,
                orderItems,
                addressFormated,
                LocalDateTime.now()
        );

        var orderEntity = orderRepository.save(order);

        log.info("Producer: Enviando pedido de pagamento");

        transactionTemplate.send(TOPIC, new SendOrderPaymentRequestDto(
                order.getTotal(),
                order.getUserId(),
                RESTAURANT_ID,
                "ORDER_PAYMENT",
                orderEntity.getId()
        ));

        return orderEntity;
    }

    @Transactional
    public Order update(UpdateOrderDto data){
        if(data.id() == null){
            throw new InvalidOrderUpdateException("Invalid data for updating the order");
        }
        var order = orderRepository.findById(data.id());

        if(order.isEmpty()){
            throw new OrderNotFoundException("Order cannot be found");
        }

        if(!order.get().getStatus().equals(OrderStatus.WAITING_PAYMENT)){
            throw new InvalidOrderUpdateException("Cannot update a order that is alredy been prepared");
        }

        if(data.address() != null){
            order.get().setAddress(formatAddress(data.address()));
        }

        updateOrderItems(order.get(), data.items(), data.remove());


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

    public Order findById(Long id){
        var order  = orderRepository.findById(id);

        if(order.isEmpty()){
            throw new OrderNotFoundException("Cannot find the order by id: "+ id);
        }

        return order.get();
    }

    private void updateOrderItems(Order order, List<OrderOrderItemDto> items, boolean remove){
        items.forEach(
                o -> {
                    var item = orderItemRepository.findById(o.id())
                            .orElseThrow(() -> new OrderItemNotFoundException("Cannot found the order item for update"));

                    if (remove) {
                        order.removeItem(item, o.quantity());
                    } else {
                        order.addItem(item,o.quantity());
                    }
                    order.calculatePrice(item.getPrice(),o.quantity());
                }
        );
    }

    @Transactional
    public void updateOrderStatus(OrderPaymentConfirmedDto status){
        var order = orderRepository.findById(status.orderId());

        if(order.isEmpty()){
            throw new OrderNotFoundException("Order not found while attempting to update the status " + status.orderId());
        }
        if(status.validPayment()){
            order.get().setStatus(OrderStatus.PREPARING);
        }else{
            order.get().setStatus(OrderStatus.PAYMENT_ERROR);
        }

        notificationTemplate.send(
                "notification.order",
                new SendOrderNotificationDto(
                                order.get().getUserId(),
                        "Status do pedido: " + order.get().getStatus().toString().toLowerCase(),
                                order.get().getId(),
                            "PAYMENT"
                )
        );

        orderRepository.save(order.get());
    }
}
