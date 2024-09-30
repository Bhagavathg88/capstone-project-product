package com.spring.microservices.model;

import lombok.Data;

@Data
public class Inventory {

    private Long id;
    private Long productId;
    private Integer quantity;
    private Integer zipcode;
    private String inventoryName;
    private String inventoryCode;
}
