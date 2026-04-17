package com.weddingplanner.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wedding_id")
    private Wedding wedding;

    private BigDecimal amount;
    private String type; // deposit, full payment, vendor payment
    private String status; // pending, completed, failed
    private LocalDateTime paymentDate;
    private String paymentMethod; // credit card, bank transfer, etc.
    private String transactionId;

}
