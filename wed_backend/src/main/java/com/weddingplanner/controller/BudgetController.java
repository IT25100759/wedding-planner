package com.weddingplanner.controller;

import com.weddingplanner.entity.Budget;
import com.weddingplanner.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @PostMapping
    public Budget addBudget(@RequestBody Budget budget) {
        return budgetService.saveBudget(budget);
    }

    @GetMapping("/wedding/{weddingId}")
    public List<Budget> getBudgetsByWedding(@PathVariable Long weddingId) {
        return budgetService.getBudgetsByWeddingId(weddingId);
    }

    @GetMapping("/wedding/{weddingId}/total")
    public String getTotalBudget(@PathVariable Long weddingId) {
        return String.format("%.2f", budgetService.getTotalBudget(weddingId));
    }

    @GetMapping("/wedding/{weddingId}/spent")
    public String getTotalSpent(@PathVariable Long weddingId) {
        return String.format("%.2f", budgetService.getTotalSpent(weddingId));
    }
}