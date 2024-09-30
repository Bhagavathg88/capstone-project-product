package com.spring.microservices.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private Double price;
    private String category;
    private String description;
    private List<Inventory> stocks;
}
