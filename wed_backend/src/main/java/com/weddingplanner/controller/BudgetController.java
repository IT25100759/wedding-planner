package com.weddingplanner.controller;

import com.weddingplanner.entity.Budget;
import com.weddingplanner.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "*")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping("/wedding/{weddingId}")
    public List<Budget> getBudgetsByWedding(@PathVariable Long weddingId) {
        return budgetService.getBudgetsByWeddingId(weddingId);
    }

    @PostMapping
    public Budget createBudget(@RequestBody Budget budget) {
        return budgetService.createBudget(budget);
    }

    @PutMapping("/{id}")
    public Budget updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
        return budgetService.updateBudget(id, budget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/wedding/{weddingId}/total")
    public BigDecimal getTotalBudget(@PathVariable Long weddingId) {
        return budgetService.getTotalBudget(weddingId);
    }

    @GetMapping("/wedding/{weddingId}/spent")
    public BigDecimal getTotalSpent(@PathVariable Long weddingId) {
        return budgetService.getTotalSpent(weddingId);
    }
}
