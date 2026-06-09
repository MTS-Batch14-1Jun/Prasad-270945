package com.techacademy.trainbase.controller;

import com.techacademy.trainbase.response.ApiResponse;
import com.techacademy.trainbase.service.PaymentServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentServiceClient paymentClient;

    // ========== Circuit Breaker Endpoint ==========
    
    @GetMapping("/process/{orderId}")
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    @Retry(name = "paymentService")
    public ResponseEntity<ApiResponse<String>> processPayment(
            @PathVariable String orderId,
            @RequestParam(defaultValue = "100.0") double amount) throws TimeoutException {
        
        String result = paymentClient.processPayment(orderId, amount);
        return ResponseEntity.ok(ApiResponse.success("Payment processed", result));
    }

    public ResponseEntity<ApiResponse<String>> paymentFallback(
            String orderId, double amount, Throwable t) {
        log.error("Fallback triggered for order: {} | Error: {}", orderId, t.getMessage());
        
        return ResponseEntity.ok(ApiResponse.success(
            "Payment service unavailable. Order queued for retry.",
            "FALLBACK_QUEUED_" + orderId));
    }

    // ========== Rate Limiter Endpoint ==========
    
    @GetMapping("/rate-limited/{orderId}")
    @RateLimiter(name = "paymentService", fallbackMethod = "rateLimitFallback")
    public ResponseEntity<ApiResponse<String>> rateLimitedPayment(
            @PathVariable String orderId) throws TimeoutException {
        
        String result = paymentClient.processPayment(orderId, 50.0);
        return ResponseEntity.ok(ApiResponse.success("Payment processed with rate limit", result));
    }

    public ResponseEntity<ApiResponse<String>> rateLimitFallback(
            String orderId, Throwable t) {
        log.warn("Rate limit exceeded for order: {}", orderId);
        
        return ResponseEntity.status(429)
            .body(ApiResponse.error("Too many requests. Please try again later."));
    }

    // ========== Bulkhead Endpoint ==========
    
    @GetMapping("/bulkhead/{orderId}")
    @Bulkhead(name = "paymentService", type = Bulkhead.Type.THREADPOOL, 
              fallbackMethod = "bulkheadFallback")
    @TimeLimiter(name = "paymentService")
    public CompletionStage<ResponseEntity<ApiResponse<String>>> bulkheadPayment(
            @PathVariable String orderId) {
        
        return CompletableFuture.supplyAsync(() -> {
            String result = null;
            try {
                result = paymentClient.processPayment(orderId, 75.0);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok(ApiResponse.success("Payment via bulkhead", result));
        });
    }

    public CompletionStage<ResponseEntity<ApiResponse<String>>> bulkheadFallback(
            String orderId, Throwable t) {
        log.warn("Bulkhead limit reached for order: {} | Error: {}", orderId, t.getMessage());
        
        return CompletableFuture.completedFuture(
            ResponseEntity.status(503)
                .body(ApiResponse.error("Service busy. Please try again later.")));
    }

    // ========== Combined Resilience Endpoint ==========
    
    @GetMapping("/resilient/{orderId}")
    @CircuitBreaker(name = "paymentService", fallbackMethod = "combinedFallback")
    @Retry(name = "paymentService")
    @RateLimiter(name = "paymentService")
    @Bulkhead(name = "paymentService", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<ApiResponse<String>> fullyResilientPayment(
            @PathVariable String orderId) throws TimeoutException {
        
        String result = paymentClient.processPayment(orderId, 200.0);
        return ResponseEntity.ok(ApiResponse.success("Fully resilient payment", result));
    }

    public ResponseEntity<ApiResponse<String>> combinedFallback(
            String orderId, Throwable t) {
        log.error("All resilience patterns exhausted for order: {} | Error: {}", 
            orderId, t.getMessage());
        
        return ResponseEntity.status(503)
            .body(ApiResponse.error(
                "All retries exhausted. Payment failed. Please contact support."));
    }
}