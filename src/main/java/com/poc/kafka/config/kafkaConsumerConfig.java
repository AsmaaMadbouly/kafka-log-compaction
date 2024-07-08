package com.poc.kafka.config;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.poc.kafka.cassandra.entities.UserMail;

@EnableKafka
@Configuration("NotificationConfiguration")
public class kafkaConsumerConfig {

    private String bootstrapServers = "localhost:9094";

    @Bean("NotificationConsumerFactory")
    public ConsumerFactory<String, UserMail> createUserMailConsumerFactory() {
        JsonDeserializer<UserMail> deserializer = new JsonDeserializer<>(UserMail.class,false);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "userConsumer");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        
        return new DefaultKafkaConsumerFactory<>(props,new StringDeserializer(),deserializer);
    }

    @Bean("NotificationContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, UserMail> createUserMailKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserMail> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createUserMailConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}