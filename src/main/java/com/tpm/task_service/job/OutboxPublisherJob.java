package com.tpm.task_service.job;

import com.tpm.task_service.config.OutboxProperties;
import com.tpm.task_service.entity.OutboxEvent;
import com.tpm.task_service.event.KafkaOutboxPublisher;
import com.tpm.task_service.repository.OutboxEventRepository;
import com.tpm.task_service.type.OutboxEventStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisherJob {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaOutboxPublisher kafkaOutboxPublisher;
    private final OutboxProperties outboxProperties;

    @Scheduled(fixedDelayString = "${app.outbox.fixed-delay-ms}")
    public void publishPendingEvents() {

        List<OutboxEvent> events = new ArrayList<>(outboxEventRepository.findByStatusAndNextRetryAtIsNullOrderByCreatedAtAsc(
                OutboxEventStatus.NEW,
                PageRequest.of(0, outboxProperties.getBatchSize())
        ));

        int remaining = outboxProperties.getBatchSize() - events.size();
        if (remaining > 0) {
            events.addAll(outboxEventRepository.findByStatusAndNextRetryAtBeforeOrderByCreatedAtAsc(
                    OutboxEventStatus.FAILED,
                    Instant.now(),
                    PageRequest.of(0, remaining)
            ));
        }

        for (OutboxEvent event : events) {
            processSingleEvent(event.getId());
        }
    }

    @Transactional
    public void processSingleEvent(UUID eventId) {
        OutboxEvent event = outboxEventRepository.findById(eventId)
                .orElseThrow();

        if (event.getStatus() == OutboxEventStatus.SENT) {
            return;
        }

        try {
            kafkaOutboxPublisher.publish(event);

            event.setStatus(OutboxEventStatus.SENT);
            event.setProcessedAt(Instant.now());
            event.setErrorMessage(null);
            event.setNextRetryAt(null);

        } catch (Exception e) {
            int attempts = event.getAttemptCount() + 1;
            event.setAttemptCount(attempts);
            event.setStatus(OutboxEventStatus.FAILED);
            event.setErrorMessage(truncate(e.getMessage(), 2000));

            if (attempts < outboxProperties.getMaxAttempts()) {
                event.setNextRetryAt(Instant.now().plusMillis(outboxProperties.getRetryDelayMs()));
            } else {
                event.setNextRetryAt(null);
            }

            log.error("Failed to publish outbox event id={}", event.getId(), e);
        }
    }

    private String truncate(String value, int max) {
        if (value == null) {
            return null;
        }
        return value.length() <= max ? value : value.substring(0, max);
    }
}
