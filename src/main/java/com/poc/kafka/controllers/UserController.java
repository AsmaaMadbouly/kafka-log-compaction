package com.poc.kafka.controllers;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.kafka.cassandra.entities.UserMail;
import com.poc.kafka.services.KafkaProducer;
import com.poc.kafka.services.UserMailService;

@RestController
@RequestMapping("/kafka-producer")
public class UserController {

	@Autowired
	private KafkaProducer kafkaProducer;
	@Autowired
	private UserMailService userMailService;
	
	@PostMapping("/addToKafkaTopic")
	public String addNewUserToKafkaTopic(@RequestBody UserMail userMail) throws ExecutionException, InterruptedException {
		boolean result = kafkaProducer.sendCreateUserMailEvent(userMail);
		return result == true ? "User added to topic successfully" : "An issue encountered during adding user to kafka topic";
	}
	
	@DeleteMapping("/deleteEventFromKafkaTopic")
	public String deleteUserFromKafkaTopic(@RequestParam String key) throws ExecutionException, InterruptedException {
		boolean result = kafkaProducer.deleteUserFromKafkaTopicUsingRetentionTime(key);
		return result == true ? "User deleted from topic successfully" : "An issue encountered during deleting user from kafka topic";
	}
	
	@DeleteMapping("/deleteUser")
	public String deleteUser(@RequestParam String key) throws ExecutionException, InterruptedException {
		userMailService.deleteUser(key);
		return "User deleted successfully";
	}
	
}
