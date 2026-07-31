package com.luisalvarez.ecom.inventoryservice.controller;


import com.luisalvarez.ecom.inventoryservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{productId}")
    public String placeOrder(@PathVariable Long productId){
        return orderService.placeOrder(productId);
    }

}
