package com.tpm.task_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.outbox")
public class OutboxProperties {
    private int batchSize = 100;
    private long fixedDelayMs = 5000;
    private int maxAttempts = 10;
    private long retryDelayMs = 30000;
}
