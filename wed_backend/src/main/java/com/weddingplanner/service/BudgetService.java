package com.weddingplanner.service;

import com.weddingplanner.entity.Budget;
import com.weddingplanner.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    public List<Budget> getBudgetsByWeddingId(Long weddingId) {
        return budgetRepository.findByWeddingId(weddingId);
    }

    public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Double getTotalBudget(Long weddingId) {
        Double total = budgetRepository.getTotalBudget(weddingId);
        return total != null ? total : 0.0;
    }

    public Double getTotalSpent(Long weddingId) {
        Double spent = budgetRepository.getTotalSpent(weddingId);
        return spent != null ? spent : 0.0;
    }
}