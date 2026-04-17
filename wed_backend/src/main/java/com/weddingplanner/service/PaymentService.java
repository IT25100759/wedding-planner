package com.weddingplanner.service;

import com.weddingplanner.entity.Payment;
import com.weddingplanner.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getPaymentsByWeddingId(Long weddingId) {
        return paymentRepository.findByWeddingId(weddingId);
    }

    public Payment createPayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(Long id, String status) {
        Payment payment = paymentRepository.findById(id).orElseThrow();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    public BigDecimal getTotalPaid(Long weddingId) {
        List<Payment> payments = getPaymentsByWeddingId(weddingId);
        return payments.stream()
                .filter(p -> "completed".equals(p.getStatus()))
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
