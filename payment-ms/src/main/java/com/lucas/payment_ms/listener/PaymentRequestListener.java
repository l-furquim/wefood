package com.lucas.payment_ms.listener;

import com.lucas.payment_ms.domains.transaction.dto.*;
import com.lucas.payment_ms.services.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PaymentRequestListener {

    private static final String TRANSACTION_TOPIC = "transaction.request";
    private static final String GROUP_ID = "wefood";

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private KafkaTemplate<String, SendPaymentResponseDto> paymentKafkaTemplate;

    @KafkaListener(topics = TRANSACTION_TOPIC, groupId = GROUP_ID)
    public void receiveRequest(PaymentRequestDto data){
        log.info("Consumer: Solicitação de pagamento chegou: {}",data);

        try{
            var transaction = transactionService.create(new CreateTransactionDto(
                    data.price(),
                    data.userId(),
                    data.restaurantId(),
                    data.type(),
                    data.orderId()
            ));
            log.info("Transação realizada com sucesso.");
        }catch (Exception e){
            log.error("Erro ao realizar a transação: {}",e.getMessage());
        }
    }

}
