package com.microservice.product_service.dto;

import java.math.BigDecimal;

public class ProductResponseDTO {
    String id ;
    String name ;
    String description ;
    BigDecimal prize ;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(String id, String name, String description, BigDecimal prize) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prize = prize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrize() {
        return prize;
    }

    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }
}
