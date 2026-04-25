package com.tpm.task_service.event;

import com.tpm.task_service.entity.OutboxEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaOutboxPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publish(OutboxEvent event) {
        kafkaTemplate.send(
                event.getTopic(),
                event.getAggregateId(),
                event.getPayload()
        ).join();
    }
}
