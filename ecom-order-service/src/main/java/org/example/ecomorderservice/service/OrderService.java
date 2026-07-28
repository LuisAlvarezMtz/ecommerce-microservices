package org.example.ecomorderservice.service;

import org.example.ecomorderservice.dto.Inventory;
import org.example.ecomorderservice.exceptions.MyCustomRuntimeException;
import org.springframework.http.HttpStatusCode;
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

    public String placeOrder(Long productId){
        //TODO call inventory service to check stock
        /*String response = restTemplate.getForObject(
                "http://localhost:8081/inventory/"+productId,
                String.class
        );*/

        ResponseEntity<Inventory> entity = restClient.get()
                .uri("http://localhost:8081/inventory/{productId}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new MyCustomRuntimeException(response.getStatusCode(), response.getHeaders());
                }))
                .toEntity(Inventory.class);


        updateInventory(entity.getBody());

        return entity.getBody()!=null && entity.getBody().getQuantity()>0?
                "Order placed":
                "Order not placed";
    }

    private void updateInventory(Inventory inventory) {
        inventory.setQuantity(inventory.getQuantity()-1);

        restClient.put()
                .uri("http://localhost:8081/inventory")
                .body(inventory)
                .retrieve()
                .toBodilessEntity();
    }

}
