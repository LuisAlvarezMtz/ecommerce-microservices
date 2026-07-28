package org.example.ecomorderservice.client;

import org.example.ecomorderservice.dto.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service", url = "http://localhost:8081")
public interface InventoryClient {

    @GetMapping("inventory/{productId}")
    Inventory getInventory(@PathVariable Long productId);

}
