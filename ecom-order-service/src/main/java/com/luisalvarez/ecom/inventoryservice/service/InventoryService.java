package com.luisalvarez.ecom.inventoryservice.service;

import com.luisalvarez.ecom.inventoryservice.client.InventoryClient;
import com.luisalvarez.ecom.inventoryservice.dto.Inventory;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class InventoryService {

    private final InventoryClient inventoryClient;

    public InventoryService(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

//    @Retryable(
//            retryFor = RuntimeException.class,
//            maxAttempts = 3,
//            backoff = @Backoff(delay = 2000)
//    )
//    @RateLimiter(name = "inventoryService", fallbackMethod = "fallbackMethod")
//    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "circuitBreakerFallbackMethod")
    @TimeLimiter(name = "inventoryServiceTimeLimiter", fallbackMethod = "timeLimiterFallbackMethod")
    public CompletableFuture<Inventory> getInventory(Long productId) {
        System.out.println("Calling get inventory to product: " + productId);
        return CompletableFuture.supplyAsync(()
                ->inventoryClient.getInventory(productId));
    }


    public CompletableFuture<Inventory> timeLimiterFallbackMethod(Long productId, Throwable throwable) {
        System.out.println("Fallback Method called for product: " + productId);
        Inventory inventory = new Inventory(productId.toString(), 0);
        return CompletableFuture.completedFuture(inventory);
    }

}
