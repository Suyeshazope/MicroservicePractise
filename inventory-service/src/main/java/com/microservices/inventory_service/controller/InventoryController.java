package com.microservices.inventory_service.controller;

import com.microservices.inventory_service.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService ;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<Boolean> isInStock(@RequestParam String skuCode , @RequestParam Integer quantity){
        return ResponseEntity.ok(inventoryService.isInStock(skuCode , quantity)) ;
    }
}
