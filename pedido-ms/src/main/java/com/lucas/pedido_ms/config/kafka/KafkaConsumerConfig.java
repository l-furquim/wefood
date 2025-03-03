package com.lucas.pedido_ms.config.kafka;

import com.lucas.pedido_ms.domains.order.dto.OrderPaymentConfirmedDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaConsumerConfig {


    @Value(value = "${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapAddress;

    @Bean
    public ConsumerFactory<String, OrderPaymentConfirmedDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);

        props.put(
                ConsumerConfig.MAX_POLL_RECORDS_CONFIG,10
        );

        props.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
        );

        var jsonDeserializer = new JsonDeserializer<>(OrderPaymentConfirmedDto.class);

        jsonDeserializer.forKeys()
                .trustedPackages(("*"));

        var stringDeserializer = new StringDeserializer();

        return new DefaultKafkaConsumerFactory<>(props,stringDeserializer, jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderPaymentConfirmedDto>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, OrderPaymentConfirmedDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


}
