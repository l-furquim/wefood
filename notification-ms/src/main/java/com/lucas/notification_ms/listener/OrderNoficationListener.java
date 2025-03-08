package com.lucas.notification_ms.listener;

import com.lucas.notification_ms.domains.notification.dto.CreateNotificationDto;
import com.lucas.notification_ms.domains.notification.exceptions.ErrorSendingNotification;
import com.lucas.notification_ms.services.notification.INotificationService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;


@Slf4j
@Service
public class OrderNoficationListener {

    private static final String TOPIC = "notification.order";
    private static final String GROUP_ID = "wefood";

    @Autowired
    private INotificationService notificationService;

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void notificationOrderListener(CreateNotificationDto data){
        log.info("Consumer de notificação dos pedidos recebido: {}", data);

        try{
            notificationService.create(
                    data
            );
        }catch (ErrorSendingNotification e){
            log.info("Erro ao enviar a notificação: {}", e.getMessage());
        }
    }

}
