package com.spring.microservices.service;

import com.spring.microservices.model.Inventory;
import com.spring.microservices.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.coyote.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.locks.StampedLock;

@Service
public class ProxyService {

    private RestTemplate restTemplate;

    ProxyService(){
        restTemplate = new RestTemplate();
    }

    public List<Inventory> getInventories(Long id, Integer zipCode){

        String inventoryServiceUrl = "http://localhost:8092/inventory/" + id;

        if(null!=zipCode) {
            inventoryServiceUrl = inventoryServiceUrl + "/" + zipCode;
        }

         ResponseEntity<List<Inventory>> responseEntity = restTemplate.exchange(
                inventoryServiceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Inventory>>() {}
        );

        return responseEntity.getBody();
    }
}
