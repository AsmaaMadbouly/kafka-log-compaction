package com.poc.kafka.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poc.kafka.cassandra.entities.UserMail;
import com.poc.kafka.cassandra.repositories.UserMailRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@KafkaListener(topics = "userCreation", clientIdPrefix = "userCreation_client",containerFactory = "NotificationContainerFactory")
public class KafkaConsumer {
	@Autowired
	private UserMailService userMailService;
	
	private Map<String,Object> map;
	
	@KafkaHandler
    public void deletUserCreationListener(@Payload(required = false) KafkaNull userMail , @Header(KafkaHeaders.RECEIVED_KEY) String key, Acknowledgment ack) {
        log.info("Notification service received user creation by key {} to delete user ", key);
        System.out.println("Listener received: " + key);
        userMailService.deleteUser(key);
        ack.acknowledge();
    }
	
	@KafkaHandler
    public void createUserCreationListener(@Payload(required = false) UserMail userMail , @Header(KafkaHeaders.RECEIVED_KEY) String key, Acknowledgment ack) {
        log.info("Notification service received user creation to add new user {} ", userMail);
        System.out.println("Listener received: " + key);
        //userMailService.addNewUser(userMail);
        map = new HashMap<>();
        map.put("add", userMail);
        userMailService.applyOperation(map);
        ack.acknowledge();
    }
	
//  @KafkaListener(topics = "userCreation", clientIdPrefix = "userCreation_client",containerFactory = "NotificationContainerFactory")
//  public void createUserCreationListener(ConsumerRecord<String, UserMail> record , Acknowledgment ack) {
//      log.info("Notification service received user creation to add new user {} ", record.value());
//      System.out.println("Listener received: " + record.key());
//      userMailService.addNewUser(record.value());
//      ack.acknowledge();
//  }
	
//	@KafkaListener(topics = "userCreation",clientIdPrefix = "userCreation_client",containerFactory = "NotificationContainerFactory")
//	public void process(ConsumerRecord<String, UserMail> record) {
//	    String key = record.key();
//	    UserMail value = record.value();
//	    log.info("Key is: "+key);
//	}
	
}
