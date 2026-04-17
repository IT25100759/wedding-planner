package com.weddingplanner.repository;

import com.weddingplanner.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByWeddingId(Long weddingId);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.wedding.id = :weddingId")
    Double getTotalPaid(@Param("weddingId") Long weddingId);
}