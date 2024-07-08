package com.poc.kafka.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.kafka.cassandra.entities.UserMail;
import com.poc.kafka.cassandra.repositories.UserMailRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class UserMailService {

	@Autowired
	private UserMailRepository userMailRepository;
	
	public void applyOperation(Map<String,Object> map) {
		if(map.containsKey("add")) {
			addNewUser((UserMail)map.get("add"));
		} else if(map.containsKey("delete")) {
			deleteUser((String)map.get("delete"));
		}
	}
	
	public void addNewUser(UserMail user) {
		log.info("Rest call to add a new user {} ", user);
		userMailRepository.save(user);
	}
	
	
	public void deleteUser(String email) {
		log.info("Rest call to delete user by email {} ", email);
		email = email.replaceAll("^\"|\"$", ""); 
		log.info("formatted email: " + email);
	    Optional<UserMail> userOptional = userMailRepository.findByEmail(email);
	    if (userOptional.isPresent()) {
			UserMail user = userOptional.get();
			boolean isProcessed = userMailRepository.deleteByEmail(user.getEmail());
			log.info("Is user deleted from db: " + isProcessed);
	    } else {
	        log.info("User with email {} is not found.", email);
	    }
	}
}
