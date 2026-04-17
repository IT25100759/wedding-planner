package com.weddingplanner.controller;

import com.weddingplanner.entity.Payment;
import com.weddingplanner.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/wedding/{weddingId}")
    public List<Payment> getPaymentsByWedding(@PathVariable Long weddingId) {
        return paymentService.getPaymentsByWeddingId(weddingId);
    }

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    @PutMapping("/{id}/status")
    public Payment updatePaymentStatus(@PathVariable Long id, @RequestParam String status) {
        return paymentService.updatePaymentStatus(id, status);
    }

    @GetMapping("/wedding/{weddingId}/total")
    public BigDecimal getTotalPaid(@PathVariable Long weddingId) {
        return paymentService.getTotalPaid(weddingId);
    }
}
