package com.microservice.order_service.controller;

import com.microservice.order_service.dto.OrderRequest;
import com.microservice.order_service.model.Order;
import com.microservice.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService ;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
//            ("/placeorder")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return new ResponseEntity<>("Order placed successfully", HttpStatus.OK);
    }
}
