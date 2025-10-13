package com.ngo.model;
import com.ngo.enums.OrderItemStatus;
import lombok.Data;

import java.util.*;

@Data
public class OrderItem {
    int id;
    int orderId;
    int userId;
    String userName;
    int productId;
    String productName;
    String productDescription;
    String productCategoryName;
    String productImageFile;
    double quantity;
    double price;
    OrderItemStatus status;
    Date created;
    Date lastUpdated;
}
