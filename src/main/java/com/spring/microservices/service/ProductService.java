package com.spring.microservices.service;

import com.spring.microservices.model.Product;
import com.spring.microservices.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public List<Product> createProducts(List<Product> products){
        return productRepository.saveAll(products);
    }


    public Product updateProduct(Product product, Long id) {
        Product availableProduct = retrieveProduct(id).get();
        availableProduct.setName(null!=product.getName()?product.getName():availableProduct.getName());
        availableProduct.setPrice(null!=product.getPrice()?product.getPrice():availableProduct.getPrice());
        return  productRepository.save(availableProduct);

    }

    public List<Product> retrieveProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> retrieveProduct(Long id) {
       return productRepository.findById(id);

    }
}
