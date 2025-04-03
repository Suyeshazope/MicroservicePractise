package com.microservice.order_service.service;

import com.microservice.order_service.client.InventoryClient;
import com.microservice.order_service.dto.OrderRequest;
import com.microservice.order_service.event.OrderPlacedEvent;
import com.microservice.order_service.model.Order;
import com.microservice.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository ;
    private final InventoryClient  inventoryClient ;
    private final KafkaTemplate<String , OrderPlacedEvent> kafkaTemplate ;

    public OrderService(OrderRepository orderRepository, InventoryClient inventoryClient, KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.kafkaTemplate = kafkaTemplate;
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

           OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent() ;
           orderPlacedEvent.setOrderNumber(order.getOrderNumber());
           orderPlacedEvent.setEmail(orderRequest.getUserDetails().email());
//           orderPlacedEvent.setFirstName(orderRequest.getUserDetails().firstName());
//           orderPlacedEvent.setLastName(orderRequest.getUserDetails().lastName());

           log.info("order placed event.... {} ......" , orderPlacedEvent);

           log.info("Start - Sending OrderPlacedEvent {} to kafka topic order-placed" , orderPlacedEvent);
           kafkaTemplate.send("order-placed" , orderPlacedEvent) ;
           log.info("End - Sending OrderPlacedEvent {} to kafka topic order-placed" , orderPlacedEvent);
       }else{
           throw new RuntimeException("Product with skuCode " + orderRequest.getSkuCode() + " is not in stock") ;
       }
    }
}
