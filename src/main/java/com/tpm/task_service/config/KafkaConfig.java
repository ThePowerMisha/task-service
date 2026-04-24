package com.tpm.task_service.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaTopicsProperties kafkaTopicsProperties;

    @Bean
    public NewTopic taskCreatedTopic() {
        return TopicBuilder.name(kafkaTopicsProperties.getTaskCreated())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic taskAssignedTopic() {
        return TopicBuilder.name(kafkaTopicsProperties.getTaskAssigned())
                .partitions(1)
                .replicas(1)
                .build();
    }
}
