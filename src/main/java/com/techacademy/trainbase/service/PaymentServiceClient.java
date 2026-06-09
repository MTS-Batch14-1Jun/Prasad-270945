package com.techacademy.trainbase.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class PaymentServiceClient {

    private final Random random = new Random();
    private int callCount = 0;

    /**
     * Simulates calling an external payment gateway.
     * Randomly fails or times out to test resilience patterns.
     */
    public String processPayment(String orderId, double amount) throws TimeoutException {
        callCount++;
        log.info("Processing payment for order: {} | Amount: ${} | Call #{}", 
            orderId, amount, callCount);

        // Simulate random failures (40% failure rate)
        int randomValue = random.nextInt(10);
        
        if (randomValue < 2) {
            // Simulate network timeout (20% chance)
            log.warn("Payment gateway timeout for order: {}", orderId);
            throw new java.util.concurrent.TimeoutException(
                "Payment gateway timed out for order: " + orderId);
        } else if (randomValue < 4) {
            // Simulate server error (20% chance)
            log.warn("Payment gateway 500 error for order: {}", orderId);
            throw new RuntimeException(
                "Payment gateway internal error for order: " + orderId);
        }

        // Simulate processing delay (100-500ms)
        try {
            int delay = 100 + random.nextInt(400);
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String result = "PAYMENT_SUCCESS_" + orderId + "_TX" + System.currentTimeMillis();
        log.info("Payment successful for order: {} | Result: {}", orderId, result);
        return result;
    }

    /**
     * Resets the internal call counter (useful for testing).
     */
    public void resetCallCount() {
        this.callCount = 0;
    }
}