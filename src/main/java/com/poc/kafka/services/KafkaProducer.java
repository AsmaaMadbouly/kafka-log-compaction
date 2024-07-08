package com.poc.kafka.services;

import java.util.concurrent.ExecutionException;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.poc.kafka.cassandra.entities.UserMail;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducer {
	 private final KafkaTemplate<String, UserMail> kafkaTemplate;
	 private final String createUserMailCreationTopic = "userCreation";
	 
	 public KafkaProducer(KafkaTemplate<String, UserMail> UserMailKafkaTemplate) {
	        this.kafkaTemplate = UserMailKafkaTemplate;
	 }
	 
	 public boolean sendCreateUserMailEvent(UserMail userMail) throws ExecutionException, InterruptedException {
	        SendResult<String, UserMail> sendResult = kafkaTemplate.send(createUserMailCreationTopic, userMail.getEmail(),userMail).get();
	        log.info("Create User Mail {} event sent via Kafka", userMail);
	        log.info("New user key: " + sendResult.getProducerRecord().key());
	        log.info(sendResult.toString());
	        return true;
	}
	
	public boolean deleteUserFromKafkaTopicUsingRetentionTime(String key) throws ExecutionException, InterruptedException {
		try {
			SendResult<String, UserMail> sendResult = kafkaTemplate.send(createUserMailCreationTopic, key, null).get();
			log.info("Delete User Mail by key {} event sent via Kafka", key);
			log.info(sendResult.toString());
			return true;
		} catch (Exception e) {
			log.error("Error deleting user record from Kafka topic: {}", e.getMessage());
			return false;
		}
	}
	
	public boolean sendUserEvent(UserMail userMail) throws ExecutionException, InterruptedException {
        SendResult<String, UserMail> sendResult = kafkaTemplate.send("user", userMail.getEmail(),userMail).get();
        log.info("Create User Mail {} event sent via Kafka", userMail);
        log.info("New user key: " + sendResult.getProducerRecord().key());
        log.info(sendResult.toString());
        return true;
   }
	
}
