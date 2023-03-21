package com.koltrum.koltrum.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long toUserId;
    private String amount;
    private String currency;
    private String transactionId;
    private String status;
    private LocalDateTime createdAt;

    @PrePersist
    void init(){
        this.createdAt = LocalDateTime.now();
    }
}
