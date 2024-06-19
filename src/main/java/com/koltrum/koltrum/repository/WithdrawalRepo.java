package com.koltrum.koltrum.repository;

import com.koltrum.koltrum.model.WithdrawalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawalRepo extends JpaRepository<WithdrawalTransaction, Long> {
    List<WithdrawalTransaction> findByUserId(Long user);
    List<WithdrawalTransaction> findByUserProjectId(Long id);
}
