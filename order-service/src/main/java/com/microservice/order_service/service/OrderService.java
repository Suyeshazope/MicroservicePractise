package com.microservice.order_service.service;

import com.microservice.order_service.client.InventoryClient;
import com.microservice.order_service.dto.OrderRequest;
import com.microservice.order_service.model.Order;
import com.microservice.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository ;
    private final InventoryClient inventoryClient ;

    public OrderService(OrderRepository orderRepository, InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    public void placeOrder(OrderRequest orderRequest){
        var isProductInStock = inventoryClient.isInStock(orderRequest.getSkuCode() , orderRequest.getQuantity()) ;

       if(isProductInStock) {
           Order order = new Order();
           order.setOrderNumber(UUID.randomUUID().toString());
           order.setPrice(orderRequest.getPrice());
           order.setSkuCode(orderRequest.getSkuCode());
           order.setQuantity(orderRequest.getQuantity());

           orderRepository.save(order);
       }else{
           throw new RuntimeException("Product with Skucode " + orderRequest.getSkuCode() + " is not in stock") ;
       }
    }
}
