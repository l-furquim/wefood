package com.lucas.pedido_ms.listener;

import com.lucas.pedido_ms.domains.order.enums.OrderStatus;
import com.lucas.pedido_ms.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
public class OrderPayedListener {

    private static final String TOPIC = "wefood-order-payed";
    private static final String GROUP_ID = "wefood";
    private static final Logger log = LoggerFactory.getLogger(OrderPayedListener.class);


    private final OrderService orderService;

    public OrderPayedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void receiveConfirmation(Long id){
        log.info("Consumer: id do pedido com pagamento confirmardo: {}",id);

        try{
            orderService.updateOrderStatus(OrderStatus.PREPARING, id);
        }catch (Exception e){
            log.error("Erro ao alterar o status do pedido {}",e.getMessage());
        }
    }
}
