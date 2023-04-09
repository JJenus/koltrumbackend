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
    private int length = 0;
    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @OneToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onInsert(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (plan.getDuration() == null){
            plan.setDuration("monthly");
        }
        if (length == 0)
            length = 1;
        if (plan.getDuration().equalsIgnoreCase("yearly")){
            expiresAt = LocalDateTime.now().plusYears(length);
        } else if (plan.getDuration().equalsIgnoreCase("monthly")) {
            expiresAt = LocalDateTime.now().plusMonths(length);
        } else if (plan.getDuration().equalsIgnoreCase("weekly")) {
            expiresAt = LocalDateTime.now().plusWeeks(length);
        } else if (plan.getDuration().equalsIgnoreCase("daily")) {
            expiresAt = LocalDateTime.now().plusDays(length);
        }
    }

    @PreUpdate
    public void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}
