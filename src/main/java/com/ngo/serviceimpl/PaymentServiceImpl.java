package com.ngo.serviceimpl;

import com.ngo.entity.PaymentData;
import com.ngo.enums.PaymentMethod;
import com.ngo.enums.PaymentStatus;
import com.ngo.model.Payment;
import com.ngo.repository.PaymentDataRepository;
import com.ngo.service.PaymentService;
import com.ngo.util.Transform;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDataRepository paymentDataRepository;

    private final Transform<PaymentData, Payment> transformPaymentData = new Transform<>(Payment.class);
    private final Transform<Payment, PaymentData> transformPayment = new Transform<>(PaymentData.class);

    @Override
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        Iterable<PaymentData> paymentDataRecords = paymentDataRepository.findAll();

        for (PaymentData paymentData : paymentDataRecords) {
            payments.add(transformPaymentData.transform(paymentData));
        }
        return payments;
    }

    @Override
    public Payment get(Integer id) {
        log.info("Fetching payment with ID >> {}", id);
        Optional<PaymentData> optional = paymentDataRepository.findById(id);

        if (optional.isPresent()) {
            log.info("Payment found.");
            return transformPaymentData.transform(optional.get());
        } else {
            log.error("Unable to locate payment record with ID: {}", id);
            return null;
        }
    }

    @Override
    public Payment processPayment(Payment payment) {
        log.info("Processing new payment: {}", payment.toString());
        PaymentData paymentData = transformPayment.transform(payment);

        // Set defaults
        paymentData.setStatus(PaymentStatus.Pending);
        paymentData.setTransaction_date(new Date());

        PaymentData saved = paymentDataRepository.save(paymentData);
        return transformPaymentData.transform(saved);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        log.info("Updating payment with ID: {}", payment.getId());
        Optional<PaymentData> optional = paymentDataRepository.findById(payment.getId());

        if (optional.isPresent()) {
            PaymentData paymentData = transformPayment.transform(payment);
            PaymentData updated = paymentDataRepository.save(paymentData);
            log.info("Payment updated successfully.");
            return transformPaymentData.transform(updated);
        } else {
            log.error("Payment not found with ID: {}", payment.getId());
            return null;
        }
    }

    @Override
    public void cancelPayment(Integer id) {
        log.info("Attempting to cancel payment with ID: {}", id);
        Optional<PaymentData> optional = paymentDataRepository.findById(id);

        if (optional.isPresent()) {
            PaymentData paymentData = optional.get();
            if (paymentData.getStatus() == PaymentStatus.Pending) {
                paymentData.setStatus(PaymentStatus.Failed);
                paymentDataRepository.save(paymentData);
                log.info("Payment cancelled successfully.");
            } else {
                log.warn("Cannot cancel payment. Current status: {}", paymentData.getStatus());
            }
        } else {
            log.error("Unable to locate payment record with ID: {}", id);
        }
    }

    @Override
    public Payment refundPayment(Integer id) {
        log.info("Processing refund for payment ID: {}", id);
        Optional<PaymentData> optional = paymentDataRepository.findById(id);

        if (optional.isPresent()) {
            PaymentData paymentData = optional.get();
            if (paymentData.getStatus() == PaymentStatus.Completed) {
                paymentData.setStatus(PaymentStatus.Refunded);
                PaymentData refunded = paymentDataRepository.save(paymentData);
                log.info("Payment refunded successfully.");
                return transformPaymentData.transform(refunded);
            } else {
                log.warn("Cannot refund payment with status: {}", paymentData.getStatus());
            }
        } else {
            log.error("Payment record not found with ID: {}", id);
        }
        return null;
    }

    @Override
    public List<Payment> getPaymentsByUserId(Integer userId) {
        // No repository method available — filter manually
        log.info("Retrieving all payments for user ID: {}", userId);
        List<Payment> results = new ArrayList<>();
        Iterable<PaymentData> allPayments = paymentDataRepository.findAll();

        for (PaymentData data : allPayments) {
            // Assuming userId corresponds to order_id since entity doesn't have userId
            if (data.getOrder_id() == userId) {
                results.add(transformPaymentData.transform(data));
            }
        }
        return results;
    }

    @Override
    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        // No repository method available — filter manually
        log.info("Retrieving payments by status: {}", status);
        List<Payment> results = new ArrayList<>();
        Iterable<PaymentData> allPayments = paymentDataRepository.findAll();

        for (PaymentData data : allPayments) {
            if (data.getStatus() == status) {
                results.add(transformPaymentData.transform(data));
            }
        }
        return results;
    }

    @Override
    public List<PaymentMethod> listAvailableMethods() {
        return Arrays.asList(PaymentMethod.values());
    }
}
