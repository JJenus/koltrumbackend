package com.koltrum.koltrum.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String amount;
    private String returns;
    private String description;
    private String priority;
    @OneToMany
    private List<SubscriptionBenefit> benefits;
}
