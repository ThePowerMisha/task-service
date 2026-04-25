package com.tpm.task_service.service;

import com.tpm.task_service.entity.OutboxEvent;
import com.tpm.task_service.repository.OutboxEventRepository;
import com.tpm.task_service.type.OutboxEventStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxService {
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public void saveEvent(
            String aggregateType,
            String aggregateId,
            String eventType,
            String topic,
            Object payload
    ) {
        OutboxEvent event = new OutboxEvent();
        event.setId(UUID.randomUUID());
        event.setAggregateType(aggregateType);
        event.setAggregateId(aggregateId);
        event.setEventType(eventType);
        event.setTopic(topic);
        event.setPayload(toJson(payload));
        event.setStatus(OutboxEventStatus.NEW);
        event.setAttemptCount(0);
        event.setCreatedAt(Instant.now());

        outboxEventRepository.save(event);
    }

    private String toJson(Object payload) {
        return objectMapper.writeValueAsString(payload);
    }

}
