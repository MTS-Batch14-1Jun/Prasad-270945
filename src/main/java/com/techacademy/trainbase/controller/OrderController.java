package com.techacademy.trainbase.controller;

import com.techacademy.trainbase.dto.OrderEvent;
import com.techacademy.trainbase.kafka.OrderProducer;
import com.techacademy.trainbase.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    
    private final OrderProducer orderProducer;
    
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<String>> sendOrder(@RequestBody OrderEvent orderEvent) {
        log.info("Received order send request for Order ID: {}", orderEvent.getOrderId());
        
        try {
            if (orderEvent.getOrderId() == null || orderEvent.getOrderId().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Order ID is required"));
            }

            orderProducer.sendOrderAsync(orderEvent);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.success("Order sent successfully to Kafka topic"));
        } catch (Exception e) {
            log.error("Error sending order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Failed to send order: " + e.getMessage()));
        }
    }

    @PostMapping("/async")
    public ResponseEntity<String> publishOrder(@RequestBody OrderEvent order) {
        orderProducer.sendOrderAsync(order);
        return ResponseEntity.accepted().body("Order placed successfully. Processing in background.");
    }



}
