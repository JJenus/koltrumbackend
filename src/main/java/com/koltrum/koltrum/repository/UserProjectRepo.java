package com.koltrum.koltrum.repository;

import com.koltrum.koltrum.model.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProjectRepo extends JpaRepository<UserProject, Long> {
    List<UserProject> findByUserId(Long id);
}