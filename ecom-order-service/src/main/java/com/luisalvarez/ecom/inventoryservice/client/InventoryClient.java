package com.luisalvarez.ecom.inventoryservice.client;

import com.luisalvarez.ecom.inventoryservice.client.config.InventoryFeignClientConfig;
import com.luisalvarez.ecom.inventoryservice.dto.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ecom-inventory-service",
        configuration = InventoryFeignClientConfig.class
)
public interface InventoryClient {

    @GetMapping("inventory/{productId}")
    Inventory getInventory(@PathVariable Long productId);

    @PutMapping("/inventory")
    String updateInventory(@RequestBody Inventory inventory);

}
