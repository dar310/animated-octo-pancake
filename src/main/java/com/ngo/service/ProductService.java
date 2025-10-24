package com.ngo.service;

import com.ngo.model.Product;
import com.ngo.model.ProductCategory;

import java.util.*;

public interface ProductService {

    List<Product> getAllProducts();
    Product[] getAll();
    Product get(Integer id);
    Product create(Product product);
    Product update(Product product);
    void delete(Integer id);
    Map<String, List<Product>> getCategoryMappedProducts();
    List<ProductCategory> listProductCategories();
    Product partialUpdate(Integer id, Map<String, Object> updates);

}
