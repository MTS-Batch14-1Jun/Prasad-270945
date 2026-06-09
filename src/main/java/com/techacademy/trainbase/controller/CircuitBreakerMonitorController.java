package com.techacademy.trainbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/monitor")
@RequiredArgsConstructor
public class CircuitBreakerMonitorController {

    @GetMapping("/resilience-summary")
    public ResponseEntity<Map<String, Object>> getResilienceSummary() {
        Map<String, Object> result = new HashMap<>();
        result.put("service", "paymentService");
        result.put("mode", "annotation-based");
        result.put("availablePatterns", new String[] {
            "@CircuitBreaker", "@Retry", "@RateLimiter", "@Bulkhead", "@TimeLimiter"
        });
        result.put("fallbackStrategy", "method-level fallback methods");
        result.put("sampleService", "PaymentResilienceService");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/resilience-summary/{orderId}")
    public ResponseEntity<Map<String, Object>> getSampleExecution(
            @PathVariable String orderId) {
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", orderId);
        result.put("description", "Call the annotated service methods to observe circuit breaker, retry, rate limit, bulkhead, and timeout behavior.");
        return ResponseEntity.ok(result);
    }
}