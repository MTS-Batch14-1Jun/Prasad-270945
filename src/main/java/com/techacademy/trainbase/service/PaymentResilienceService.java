package com.techacademy.trainbase.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentResilienceService {

    private final PaymentServiceClient paymentServiceClient;

    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    @Retry(name = "paymentService")
    public String processWithCircuitBreakerAndRetry(String orderId, double amount) throws TimeoutException {
        return paymentServiceClient.processPayment(orderId, amount);
    }

    @RateLimiter(name = "paymentService", fallbackMethod = "rateLimitFallback")
    public String processWithRateLimit(String orderId, double amount) throws TimeoutException {
        return paymentServiceClient.processPayment(orderId, amount);
    }

    @Bulkhead(name = "paymentService", fallbackMethod = "bulkheadFallback")
    @TimeLimiter(name = "paymentService")
    public CompletableFuture<String> processAsync(String orderId, double amount) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return paymentServiceClient.processPayment(orderId, amount);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String paymentFallback(String orderId, double amount, Throwable ex) {
        log.warn("Circuit breaker fallback for order {}: {}", orderId, ex.getMessage());
        return "PAYMENT_FALLBACK_" + orderId;
    }

    private String rateLimitFallback(String orderId, double amount, Throwable ex) {
        log.warn("Rate limiter fallback for order {}: {}", orderId, ex.getMessage());
        return "RATE_LIMITED_" + orderId;
    }

    private String bulkheadFallback(String orderId, double amount, Throwable ex) {
        log.warn("Bulkhead fallback for order {}: {}", orderId, ex.getMessage());
        return "BULKHEAD_REJECTED_" + orderId;
    }
}