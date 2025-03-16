package com.lucas.mailsender_ms.listener;


import com.lucas.mailsender_ms.domains.mail.dto.SendMailDto;
import com.lucas.mailsender_ms.services.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendMailListener {

    private static final String TOPIC = "mail.request";
    private static final String GROUP_ID = "wefood";

    @Autowired
    private IMailService mailService;

    @Autowired
    private KafkaTemplate<String, SendMailDto> paymentKafkaTemplate;

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void receiveRequest(SendMailDto data) {
        log.info("Consumer: Solicitação de envio de email chegou: {}", data);

    }
}
