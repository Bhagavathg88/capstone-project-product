package com.spring.microservices.controller;

import com.spring.microservices.model.Product;
import com.spring.microservices.model.ProductResponse;
import com.spring.microservices.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping("/products")
    public List<Product> createProduct(@RequestBody List<Product> products){
       return productService.createProducts(products);
    }

    @PatchMapping("/product/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable  Long id){

        return productService.updateProduct(product, id);
    }

    @GetMapping("/products")
    public List<ProductResponse> retrieveProducts(){
        return productService.retrieveProducts();
    }

    @GetMapping("/product/{id}")
    public List<ProductResponse> retrieveProduct(@PathVariable Long id) {

        return productService.retrieveProduct(id);
    }

    @GetMapping("/product/{id}/{zipCode}")
    public ProductResponse retrieveProduct(@PathVariable Long id, @PathVariable Integer zipCode) {

        return productService.retrieveProductByZipcode(id, zipCode);
    }
}
