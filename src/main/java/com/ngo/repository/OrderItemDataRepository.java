package com.ngo.repository;

import com.ngo.entity.OrderItemData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemDataRepository extends CrudRepository<OrderItemData, Integer> {
    List<OrderItemData> findAllByUserId(Integer customerId);
//    List<OrderItemData> saveAll(List<OrderItemData> orderItemData);
}
