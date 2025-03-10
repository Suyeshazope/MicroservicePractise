package com.microservice.product_service.dto;

import java.math.BigDecimal;

public class ProductDTO {
    String name ;
    String description ;
    BigDecimal prize ;

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
