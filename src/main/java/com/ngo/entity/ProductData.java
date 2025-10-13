package com.ngo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ngo.enums.ProductFormat;
import com.ngo.enums.ProductMediaType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "product_data")
public class    ProductData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;
    String description;
    String categoryName;
    ProductMediaType mediaType;
    ProductFormat format;
    double price;
    int stockQuantity;
    Date releaseDate;
    String publisher;
    String ratingAge;
    String imageFile;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date lastUpdated;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date created;
}
