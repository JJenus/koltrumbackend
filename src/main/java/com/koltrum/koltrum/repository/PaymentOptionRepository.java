package com.koltrum.koltrum.repository;

import com.koltrum.koltrum.model.PaymentOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOptionRepository extends JpaRepository<PaymentOption, Long> {
}