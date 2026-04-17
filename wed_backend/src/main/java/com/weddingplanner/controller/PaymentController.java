package com.weddingplanner.controller;

import com.weddingplanner.entity.Payment;
import com.weddingplanner.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment addPayment(@RequestBody Payment payment) {
        return paymentService.savePayment(payment);
    }

    @GetMapping("/wedding/{weddingId}")
    public List<Payment> getPaymentsByWedding(@PathVariable Long weddingId) {
        return paymentService.getPaymentsByWeddingId(weddingId);
    }

    @GetMapping("/wedding/{weddingId}/total")
    public String getTotalPaid(@PathVariable Long weddingId) {
        return String.format("%.2f", paymentService.getTotalPaid(weddingId));
    }
}