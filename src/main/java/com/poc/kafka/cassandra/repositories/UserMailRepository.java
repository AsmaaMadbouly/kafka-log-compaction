package com.poc.kafka.cassandra.repositories;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.poc.kafka.cassandra.entities.UserMail;

@Repository
public interface UserMailRepository extends CassandraRepository<UserMail, String> {
	Optional<UserMail> findByEmail(String email);
	boolean deleteByEmail(String email);
}
