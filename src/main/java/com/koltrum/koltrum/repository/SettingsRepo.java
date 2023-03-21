package com.koltrum.koltrum.repository;

import com.koltrum.koltrum.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepo extends JpaRepository<Setting, Long> {
}
