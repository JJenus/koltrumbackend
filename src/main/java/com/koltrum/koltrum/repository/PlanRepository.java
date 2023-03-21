package com.koltrum.koltrum.repository;

import com.koltrum.koltrum.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}