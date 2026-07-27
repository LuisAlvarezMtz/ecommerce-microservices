package org.example.ecomorderservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private final RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String placeOrder(String productId){
        //TODO call inventory service to check stock
        String response = restTemplate.getForObject(
                "http://localhost:8081/inventory/"+productId,
                String.class
        );

        return "In Stock".equals(response)?
                "Order placed":
                "Order not placed";
    }

}
