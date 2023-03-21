package com.koltrum.koltrum.repository;

import com.koltrum.koltrum.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project, Long> {
}
