package org.example.ecomorderservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private final RestTemplate restTemplate;
    private final RestClient restClient;

    public OrderService(RestTemplate restTemplate, RestClient restClient) {
        this.restTemplate = restTemplate;
        this.restClient = restClient;
    }

    public String placeOrder(String productId){
        //TODO call inventory service to check stock
        /*String response = restTemplate.getForObject(
                "http://localhost:8081/inventory/"+productId,
                String.class
        );*/

        ResponseEntity<String> entity = restClient.get()
                .uri("http://localhost:8081/inventory/{productId}", productId)
                .retrieve()
                .toEntity(String.class);
        System.out.println(entity.getStatusCode());
        return "In Stock".equals(entity.getBody())?
                "Order placed":
                "Order not placed";
    }

}
