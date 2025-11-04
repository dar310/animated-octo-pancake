package com.ngo.model;
import com.ngo.enums.OrderItemStatus;
import lombok.Data;

import java.util.*;

@Data
public class OrderItem {
    int id;
    int orderId;
    int userId;
    String user_fName;
    String user_lName;
    int productId;
    String productName;
    String productDescription;
    String productCategoryName;
    String productImageFile;
    int quantity;
    double price;
    OrderItemStatus status;
    Date created;
    Date lastUpdated;
}
