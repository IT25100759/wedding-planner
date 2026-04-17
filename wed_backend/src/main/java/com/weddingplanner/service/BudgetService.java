package com.weddingplanner.service;

import com.weddingplanner.entity.Budget;
import com.weddingplanner.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public List<Budget> getBudgetsByWeddingId(Long weddingId) {
        return budgetRepository.findByWeddingId(weddingId);
    }

    public Budget createBudget(Budget budget) {
        budget.setRemainingAmount(budget.getAllocatedAmount().subtract(budget.getSpentAmount()));
        return budgetRepository.save(budget);
    }

    public Budget updateBudget(Long id, Budget budgetDetails) {
        Budget budget = budgetRepository.findById(id).orElseThrow();
        budget.setAllocatedAmount(budgetDetails.getAllocatedAmount());
        budget.setSpentAmount(budgetDetails.getSpentAmount());
        budget.setRemainingAmount(budget.getAllocatedAmount().subtract(budget.getSpentAmount()));
        return budgetRepository.save(budget);
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }

    public BigDecimal getTotalBudget(Long weddingId) {
        List<Budget> budgets = getBudgetsByWeddingId(weddingId);
        return budgets.stream()
                .map(Budget::getAllocatedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalSpent(Long weddingId) {
        List<Budget> budgets = getBudgetsByWeddingId(weddingId);
        return budgets.stream()
                .map(Budget::getSpentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
