package com.tpm.task_service.repository;

import com.tpm.task_service.entity.OutboxEvent;
import com.tpm.task_service.type.OutboxEventStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
    List<OutboxEvent> findByStatusAndNextRetryAtBeforeOrderByCreatedAtAsc(
            OutboxEventStatus status,
            Instant nextRetryAt,
            Pageable pageable
    );

    List<OutboxEvent> findByStatusAndNextRetryAtIsNullOrderByCreatedAtAsc(
            OutboxEventStatus status,
            Pageable pageable
    );
}
