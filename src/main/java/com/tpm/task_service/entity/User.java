package com.tpm.task_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;
}
