package com.ngo.model;

import com.ngo.enums.PaymentMethod;
import com.ngo.enums.PaymentStatus;
import lombok.Data;

import java.util.Date;

@Data
public class Payment {
    int id;
    int order_id;
    PaymentMethod method;
    PaymentStatus status;
    Date transaction_date;
    double amount;
}
