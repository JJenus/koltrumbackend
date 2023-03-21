package com.koltrum.koltrum.repository;

import com.koltrum.koltrum.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepo extends JpaRepository<Balance, Long> {
}
