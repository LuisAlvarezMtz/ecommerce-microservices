package com.luisalvarez.ecom.inventoryservice.controller;


import com.luisalvarez.ecom.inventoryservice.dto.Inventory;
import com.luisalvarez.ecom.inventoryservice.service.InventoryService;
import com.luisalvarez.ecom.inventoryservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final InventoryService inventoryService;

    public OrderController(OrderService orderService, InventoryService inventoryService) {
        this.orderService = orderService;
        this.inventoryService = inventoryService;
    }

    @PostMapping("/{productId}")
    public Inventory placeOrder(@PathVariable Long productId){
        return inventoryService.getInventory(productId);
    }

}
