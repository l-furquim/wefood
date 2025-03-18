package com.lucas.pedido_ms.listener;

import com.lucas.pedido_ms.domains.order.dto.OrderPaymentConfirmedDto;
import com.lucas.pedido_ms.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class OrderPayedListener {

    private static final String TOPIC = "order.confirmed";
    private static final String GROUP_ID = "wefood";
    private static final Logger log = LoggerFactory.getLogger(OrderPayedListener.class);

    @Autowired
    private OrderService orderService;


    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void receiveConfirmation(OrderPaymentConfirmedDto data){
        log.info("Consumer: id do pedido com confirmação chegou: {}",data);

        try{
            orderService.updateOrderConfirmation(data);

            log.info("Status do pedido alterado com sucesso.");
        }catch (Exception e){
            log.error("Erro ao alterar o status do pedido {}",e.getMessage());
        }

    }
}
