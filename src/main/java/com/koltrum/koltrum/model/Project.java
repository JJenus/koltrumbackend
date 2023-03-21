package com.koltrum.koltrum.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String symbol;
    private String crypto;
    private String logoUrl;
    @OneToMany
    private List<Plan> plans;
}
