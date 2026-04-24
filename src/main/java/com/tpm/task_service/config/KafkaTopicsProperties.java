package com.tpm.task_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.kafka.topics")
public class KafkaTopicsProperties {
    private String taskCreated;
    private String taskAssigned;
}
