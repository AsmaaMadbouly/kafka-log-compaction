package com.poc.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {
	
	@Bean
	public KafkaAdmin admin() {
	    Map<String, Object> configs = new HashMap<>();
	    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
	    return new KafkaAdmin(configs);
	}
 
	@Bean
	public NewTopic topic1() {
	    return TopicBuilder.name("testCompact")
	            .partitions(1)
	            .replicas(1)
	            .compact()
	            .build();
	}
	
	@Bean
	public NewTopic topic2() {
	    return TopicBuilder.name("userCreation")
	            .partitions(1)
	            .replicas(1)
	            .compact()
	            .config(TopicConfig.SEGMENT_MS_CONFIG, "5000") 
	            .config(TopicConfig.DELETE_RETENTION_MS_CONFIG, "3000") 
	            .config(TopicConfig.MIN_CLEANABLE_DIRTY_RATIO_CONFIG, "0.001") 
	            .build();
	}
}
