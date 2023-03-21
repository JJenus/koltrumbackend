package com.koltrum.koltrum.repository;

import com.koltrum.koltrum.model.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestimonialRepo extends JpaRepository<Testimonial, Long> {
}
