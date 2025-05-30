package com.microservice.order_service.client;

//import org.springframework.cloud.openfeign.FeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

//@FeignClient(value = "inventory" , url = "${inventory.url}")
public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    //    @RequestMapping(method = RequestMethod.GET , value = "/api/inventory")
    @GetExchange("/api/inventory")
    @CircuitBreaker(name = "inventory" , fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam String skuCode , @RequestParam Integer quantity) ;

    default boolean fallbackMethod(String code , Integer quantity , Throwable throwable){
        log.info("Cannot get inventory for skuCode {} , failure reason : {}" , code , throwable.getMessage());
        return false ;
    }
}
