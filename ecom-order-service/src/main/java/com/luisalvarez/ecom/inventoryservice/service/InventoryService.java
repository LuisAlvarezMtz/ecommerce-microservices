package com.luisalvarez.ecom.inventoryservice.service;

import com.luisalvarez.ecom.inventoryservice.client.InventoryClient;
import com.luisalvarez.ecom.inventoryservice.dto.Inventory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final InventoryClient inventoryClient;

    public InventoryService(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }


    @Retryable(
            retryFor = RuntimeException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public Inventory getInventory(Long productId) {
        System.out.println("Calling get inventory to product: " + productId);
        return inventoryClient.getInventory(productId);
    }

}
