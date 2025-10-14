package com.ngo.model;

import com.ngo.enums.ProductFormat;
import com.ngo.enums.ProductMediaType;
import lombok.Data;

import java.util.Date;

@Data
public class Product {
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
}
