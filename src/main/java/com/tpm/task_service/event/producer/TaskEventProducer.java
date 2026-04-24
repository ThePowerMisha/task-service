package com.tpm.task_service.event.producer;

import com.tpm.task_service.config.KafkaTopicsProperties;
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
    private final KafkaTopicsProperties kafkaTopicsProperties;

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
        kafkaTemplate.send(kafkaTopicsProperties.getTaskCreated(), String.valueOf(task.getId()), event);
    }

    public void sendTaskAssigned(Task task) {
        User user = task.getUser();

        TaskAssignedEvent event = new TaskAssignedEvent(
                task.getId(),
                user.getId(),
                Instant.now()
        );

        kafkaTemplate.send(kafkaTopicsProperties.getTaskAssigned(), String.valueOf(task.getId()), event);
    }
}
