package com.tpm.task_service.config;

import com.tpm.task_service.event.TopicNames;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic taskCreatedTopic() {
        return TopicBuilder.name(TopicNames.TASK_CREATED)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic taskAssignedTopic() {
        return TopicBuilder.name(TopicNames.TASK_ASSIGNED)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
