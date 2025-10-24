package com.ngo.controller;

import com.ngo.model.Payment;
import com.ngo.enums.PaymentStatus;
import com.ngo.enums.PaymentMethod;
import com.ngo.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // ✅ Get all payments
    @GetMapping("/api/payment")
    public ResponseEntity<?> getAllPayments() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            List<Payment> payments = paymentService.getAllPayments();
            log.info("Payments retrieved: {}", payments.size());
            response = ResponseEntity.ok(payments);
        } catch (Exception ex) {
            log.error("Failed to retrieve payments: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    // ✅ Get single payment by ID
    @GetMapping("/api/payment/{id}")
    public ResponseEntity<?> getPayment(@PathVariable final Integer id) {
        log.info("Fetching payment with ID >> {}", id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Payment payment = paymentService.get(id);
            response = ResponseEntity.ok(payment);
        } catch (Exception ex) {
            log.error("Failed to retrieve payment: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    // ✅ Create / Process a new payment
    @PostMapping("/api/payment")
    public ResponseEntity<?> processPayment(@RequestBody Payment payment) {
        log.info("Processing new payment >> {}", payment);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Payment newPayment = paymentService.processPayment(payment);
            log.info("Payment processed successfully.");
            response = ResponseEntity.ok(newPayment);
        } catch (Exception ex) {
            log.error("Failed to process payment: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    // ✅ Update existing payment
    @PutMapping("/api/payment")
    public ResponseEntity<?> updatePayment(@PathVariable final Integer id, @RequestBody Payment payment) {
        log.info("Updating payment >> {}", payment);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            payment.setId(id);
            Payment updated = paymentService.updatePayment(payment);
            response = ResponseEntity.ok(updated);
        } catch (Exception ex) {
            log.error("Failed to update payment: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    // ✅ Cancel a payment
    @DeleteMapping("/api/payment/{id}")
    public ResponseEntity<?> cancelPayment(@PathVariable final Integer id) {
        log.info("Cancelling payment ID >> {}", id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            paymentService.cancelPayment(id);
            response = ResponseEntity.ok("Payment cancelled successfully");
        } catch (Exception ex) {
            log.error("Failed to cancel payment: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    // ✅ Refund a payment
    @PostMapping("/api/payment/refund/{id}")
    public ResponseEntity<?> refundPayment(@PathVariable final Integer id) {
        log.info("Refund request for payment ID >> {}", id);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Payment refunded = paymentService.refundPayment(id);
            response = ResponseEntity.ok(refunded);
        } catch (Exception ex) {
            log.error("Failed to refund payment: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    // ✅ Get payments by status (Filtered manually in service)
    @GetMapping("/api/payment/status/{status}")
    public ResponseEntity<?> getPaymentsByStatus(@PathVariable final PaymentStatus status) {
        log.info("Fetching payments by status >> {}", status);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            List<Payment> payments = paymentService.getPaymentsByStatus(status);
            response = ResponseEntity.ok(payments);
        } catch (Exception ex) {
            log.error("Failed to retrieve payments by status: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    // ✅ Get available payment methods (enums)
    @GetMapping("/api/payment/methods")
    public ResponseEntity<?> getPaymentMethods() {
        log.info("Retrieving available payment methods");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            List<PaymentMethod> methods = paymentService.listAvailableMethods();
            response = ResponseEntity.ok(methods);
        } catch (Exception ex) {
            log.error("Failed to retrieve payment methods: {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}
