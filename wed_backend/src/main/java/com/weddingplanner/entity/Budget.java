package com.weddingplanner.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wedding_id")
    private Wedding wedding;

    private String category; // venue, photography, catering, etc.
    private BigDecimal allocatedAmount;
    private BigDecimal spentAmount;
    private BigDecimal remainingAmount;

}
