package com.spring.microservices.service;

import com.spring.microservices.model.Inventory;
import com.spring.microservices.model.Product;
import com.spring.microservices.model.ProductResponse;
import com.spring.microservices.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProxyService proxyService;

    public List<Product> createProducts(List<Product> products){
        return productRepository.saveAll(products);
    }


    public Product updateProduct(Product product, Long id) {
        Product availableProduct = retrieveProductForUpdate(id).get();
        availableProduct.setName(null!=product.getName()?product.getName():availableProduct.getName());
        availableProduct.setPrice(null!=product.getPrice()?product.getPrice():availableProduct.getPrice());
        return  productRepository.save(availableProduct);

    }

    public Optional<Product> retrieveProductForUpdate(Long id) {
        return productRepository.findById(id);
    }

    public List<ProductResponse> retrieveProducts() {
        List<Product> products = productRepository.findAll();
        return UpdateStockforProduct(products, null);

    }

    private List<ProductResponse> UpdateStockforProduct(List<Product> products, Integer zipCode) {

        return products.stream()
                .map(product -> {
                    List<Inventory> stocks = getStocksFromInventoryService(product.getId(),zipCode);
                    return mapToProductResponse(product, stocks);
                })
                .collect(Collectors.toList());
    }

    public List<ProductResponse> retrieveProduct(Long id) {

        return UpdateStockforProduct(productRepository.findById(id).stream().collect(Collectors.toList()), null);
    }

    public ProductResponse retrieveProductByZipcode(Long id, Integer zipCode){

        return UpdateStockforProduct(productRepository.findById(id).stream().collect(Collectors.toList()), zipCode).get(0);
    }

    private List<Inventory> getStocksFromInventoryService(Long productId, Integer zipCode) {
        return proxyService.getInventories(productId, zipCode);
    }

    private ProductResponse mapToProductResponse(Product product, List<Inventory> stocks) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setCategory(product.getCategory());
        response.setDescription(product.getDescription());
        response.setStocks(stocks);
        return response;
    }

}
