package com.koltrum.koltrum.repository;

import com.koltrum.koltrum.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQsRepo extends JpaRepository<FAQ, Long> {
}
