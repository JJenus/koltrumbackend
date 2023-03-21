package com.koltrum.koltrum.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String amount;
    private String currency;
    private LocalDateTime updatedAt;



    @PrePersist
    @PreUpdate
    private void update(){
        updatedAt = LocalDateTime.now();
    }
}
