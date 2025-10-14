package com.ngo.repository;

import com.ngo.entity.PaymentData;
import org.springframework.data.repository.CrudRepository;

public interface PaymentDataRepository extends CrudRepository<PaymentData, Integer> {
}
