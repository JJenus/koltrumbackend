package com.koltrum.koltrum.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class UserProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String value;
    private String status;
    private Long userId;
    private String transactionId;
    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @OneToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onInsert(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}
