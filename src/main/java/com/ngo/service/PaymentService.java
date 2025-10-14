package com.ngo.service;

import com.ngo.model.Payment;
import com.ngo.enums.PaymentStatus;
import com.ngo.enums.PaymentMethod;

import java.util.*;

public interface PaymentService {

    // Retrieve all recorded payments
    List<Payment> getAllPayments();

    // Retrieve a specific payment by ID
    Payment get(Integer id);

    // Process and create a new payment (e.g., checkout or order payment)
    Payment processPayment(Payment payment);

    // Update an existing payment (e.g., update its status after confirmation)
    Payment updatePayment(Payment payment);

    // Cancel a payment that is still pending
    void cancelPayment(Integer id);

    // Refund a completed payment
    Payment refundPayment(Integer id);

    // Retrieve payments associated with a specific user
    List<Payment> getPaymentsByUserId(Integer userId);

    // Retrieve payments filtered by status (PENDING, COMPLETED, FAILED, etc.)
    List<Payment> getPaymentsByStatus(PaymentStatus status);

    // List supported payment methods (from the PaymentMethod enum)
    List<PaymentMethod> listAvailableMethods();
}
