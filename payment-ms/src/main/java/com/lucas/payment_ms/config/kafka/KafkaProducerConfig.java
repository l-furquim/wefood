package com.lucas.payment_ms.config.kafka;

import com.lucas.payment_ms.domains.transaction.dto.SendPaymentConfirmationEmailDto;
import com.lucas.payment_ms.domains.transaction.dto.SendPaymentResponseDto;
import com.lucas.payment_ms.domains.transaction.dto.SendTransactionNotification;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapAddress;


    @Bean
    public ProducerFactory<String, SendPaymentResponseDto> paymentProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public ProducerFactory<String, SendPaymentConfirmationEmailDto> emailProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, SendPaymentConfirmationEmailDto> emailKafkaTemplate() {
        return new KafkaTemplate<>(emailProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, SendPaymentResponseDto> paymentKafkaTemplate() {
        return new KafkaTemplate<>(paymentProducerFactory());
    }


    @Bean
    public ProducerFactory<String, SendTransactionNotification> notificationProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, SendTransactionNotification> notificationKafkaTemplate() {
        return new KafkaTemplate<>(notificationProducerFactory());
    }

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
}
