package com.tpm.task_service.event.producer;

import com.tpm.task_service.entity.Task;
import com.tpm.task_service.entity.User;
import com.tpm.task_service.event.TopicNames;
import com.tpm.task_service.event.dto.TaskAssignedEvent;
import com.tpm.task_service.event.dto.TaskCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTaskCreated(Task task){

        UUID userId = null;

        if (task.getUser() != null) {
            userId = task.getUser().getId();
        }

            TaskCreatedEvent event = new TaskCreatedEvent(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                userId,
                Instant.now()
        );
        kafkaTemplate.send(TopicNames.TASK_CREATED, String.valueOf(task.getId()), event);
    }

    public void sendTaskAssigned(Task task) {
        User user = task.getUser();

        TaskAssignedEvent event = new TaskAssignedEvent(
                task.getId(),
                user.getId(),
                Instant.now()
        );

        kafkaTemplate.send(TopicNames.TASK_ASSIGNED, String.valueOf(task.getId()), event);
    }
}
