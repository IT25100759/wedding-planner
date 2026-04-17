package com.weddingplanner.repository;

import com.weddingplanner.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByWeddingId(Long weddingId);

    @Query("SELECT SUM(b.allocatedAmount) FROM Budget b WHERE b.wedding.id = :weddingId")
    Double getTotalBudget(@Param("weddingId") Long weddingId);

    @Query("SELECT SUM(b.spentAmount) FROM Budget b WHERE b.wedding.id = :weddingId")
    Double getTotalSpent(@Param("weddingId") Long weddingId);
}