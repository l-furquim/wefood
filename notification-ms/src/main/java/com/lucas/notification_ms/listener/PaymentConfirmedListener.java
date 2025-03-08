package com.lucas.notification_ms.listener;

import com.lucas.notification_ms.domains.notification.dto.CreateNotificationDto;
import com.lucas.notification_ms.domains.notification.exceptions.ErrorSendingNotification;
import com.lucas.notification_ms.services.notification.INotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentConfirmedListener {

    private static final String TOPIC = "notification.transaction";
    private static final String GROUP_ID = "wefood";

    @Autowired
    private INotificationService notificationService;

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void receiveRequest(CreateNotificationDto data){
        log.info("Consumer de notificação recebido: {}", data);

        try{
            notificationService.create(
                    data
            );
        }catch (ErrorSendingNotification e){
            log.info("Erro ao criar notificação: {}", e.getMessage());
        }
    }

}
