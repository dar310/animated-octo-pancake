package com.ngo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ngo.enums.PaymentMethod;
import com.ngo.enums.PaymentStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "payment_data")
public class PaymentData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    int order_id;
    PaymentMethod method;
    PaymentStatus status;
    Date transaction_date;
    double amount;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date lastUpdated;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date created;
}
