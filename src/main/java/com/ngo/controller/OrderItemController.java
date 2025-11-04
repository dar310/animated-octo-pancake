package com.ngo.controller;

import com.ngo.model.OrderItem;
import com.ngo.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/api/orderitem")
    public ResponseEntity<?> getAll()
    {
        log.error("Failed to retrieve orderitem");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            List<OrderItem> orderItems = orderItemService.getAll();
            response = ResponseEntity.ok( orderItems);
        }
        catch( Exception ex)
        {
            log.error("Failed to retrieve product with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }


    @GetMapping("/api/orderitem/{userId}")
    public ResponseEntity<?> getOrderItems(@PathVariable final Integer userId, @RequestParam (name="status") Integer status)
    {
        log.info("=== START: userId={}, status={}", userId, status);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            if(status == 0) {
                List<OrderItem> orderItems = orderItemService.getCartItems(userId);

                response = ResponseEntity.ok( orderItems);
            }
            else {
                List<OrderItem> orderItems = orderItemService.getOrderItems(userId);
                log.info("Calling getOrderItems");
                log.info("Service returned: {} items", orderItems.size());
                response = ResponseEntity.ok( orderItems);
            }
        }
        catch( Exception ex)
        {
            log.error("Failed to retrieve product with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("/api/orderitem")
    public ResponseEntity<?> add(@RequestBody OrderItem orderItem){
        log.info("Input >> " + orderItem.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            OrderItem  neworderItem = orderItemService.create(orderItem);
            log.info("created product >> " + neworderItem.toString() );
            response = ResponseEntity.ok(neworderItem);
        }
        catch( Exception ex)
        {
            log.error("Failed to retrieve product with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("/api/orderitems")
    public ResponseEntity<?> add(@RequestBody List<OrderItem> orderItems){
        log.info("Input >> " + orderItems.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            List<OrderItem> neworderItems = orderItemService.create(orderItems);
            log.info("created product >> " + neworderItems.toString() );
            response = ResponseEntity.ok(neworderItems);
        }
        catch( Exception ex)
        {
            log.error("Failed to retrieve product with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping("/api/orderitem")
    public ResponseEntity<?> update(@RequestBody OrderItem orderItem){
        log.info("Update Input >> orderItem.toString() ");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            OrderItem newOrderItem =orderItemService.update(orderItem);
            response = ResponseEntity.ok(newOrderItem);
        }
        catch( Exception ex)
        {
            log.error("Failed to retrieve product with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}
