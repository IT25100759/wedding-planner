package com.weddingplanner.service;

import com.weddingplanner.entity.Payment;
import com.weddingplanner.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getPaymentsByWeddingId(Long weddingId) {
        return paymentRepository.findByWeddingId(weddingId);
    }

    public Payment savePayment(Payment payment) {
        payment.setStatus("completed"); // Assuming payment is completed
        return paymentRepository.save(payment);
    }

    public Double getTotalPaid(Long weddingId) {
        Double total = paymentRepository.getTotalPaid(weddingId);
        return total != null ? total : 0.0;
    }
}