package com.techacademy.trainbase.kafka;

import com.techacademy.trainbase.dto.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Kafka Consumer for processing order events asynchronously
 *
 * This service consumes order events from Kafka topic "order-events"
 * and processes them asynchronously using a thread pool executor.
 * It implements manual offset commit strategy for better error handling
 * and retry logic.
 *
 * Features:
 * - Asynchronous processing with CompletableFuture
 * - Manual acknowledgment of messages only on successful processing
 * - Automatic retry and Dead Letter Topic (DLT) support on failures
 * - Configurable concurrency (3 threads)
 * - Error logging and exception handling
 *
 * @author Trainbase Team
 * @version 1.0
 */
@Service
@Slf4j
public class OrderConsumer {

    private final Executor taskExecutor;

    /**
     * Constructor for OrderConsumer with dependency injection
     *
     * Injects the application's task executor for asynchronous processing of order events.
     * The executor is injected by Spring and configured through the application configuration.
     *
     * @param taskExecutor the executor for async order processing
     *
     * @throws IllegalArgumentException if taskExecutor is null
     */
    @Autowired
    public OrderConsumer(@Qualifier("applicationTaskExecutor") Executor taskExecutor) {
        if (taskExecutor == null) {
            throw new IllegalArgumentException("taskExecutor cannot be null");
        }
        this.taskExecutor = taskExecutor;
        log.info("OrderConsumer initialized with executor: {}", taskExecutor.getClass().getSimpleName());
    }

    /**
     * Consume and process order events from Kafka
     *
     * This method acts as a Kafka listener that consumes messages from the
     * "order-events" topic. Each message is processed asynchronously using
     * the configured executor. On success, the message offset is acknowledged.
     * On failure, the message is NOT acknowledged, allowing Kafka to retry
     * or send to Dead Letter Topic.
     *
     * Processing flow:
     * 1. Receive OrderEvent from Kafka topic
     * 2. Submit processOrder task to executor (async)
     * 3. On success: acknowledge message offset
     * 4. On failure: log error, do NOT acknowledge (triggers retry/DLT)
     *
     * Configuration:
     * - Topic: order-events
     * - Consumer Group: notification-group
     * - Concurrency: 3 (3 concurrent consumers)
     * - Manual Acknowledgment: enabled
     *
     * @param orderEvent the order event payload from Kafka message
     *                   Contains: orderId, userId, amount, timestamp, etc.
     *
     * @param acknowledgment Spring's Acknowledgment handle for manual offset commit
     *                      Only called on successful processing
     *
     * @see OrderEvent
     * @see Acknowledgment
     * @see CompletableFuture
     */
    @KafkaListener(
        topics = "order-events",
        groupId = "notification-group",
        concurrency = "3"
    )
    public void consumeOrder1(OrderEvent orderEvent, Acknowledgment acknowledgment) {
        log.info("Received Order Event: {}", orderEvent);

        // Process asynchronously using CompletableFuture
        CompletableFuture
            // Submit order processing task to executor pool
            .runAsync(() -> processOrder(orderEvent), taskExecutor)

            // On successful completion, acknowledge the message
            .thenRun(() -> {
                acknowledgment.acknowledge();
                log.info("Successfully processed and acknowledged order: {}", orderEvent.getOrderId());
            })

            // On failure, log error but DO NOT acknowledge
            // This allows Kafka to:
            // 1. Retry the message
            // 2. Send to Dead Letter Topic (if configured)
            // 3. Re-balance and process later
            .exceptionally(ex -> {
                log.error("Failed to process order: {}", orderEvent.getOrderId(), ex);
                // Intentionally not acknowledging - let Kafka handle retry/DLT
                return null;
            });
    }

    /**
     * Process the order event (business logic)
     *
     * This method contains the core business logic for processing an order.
     * It validates the order amount and can be extended to include:
     * - Payment processing
     * - Inventory updates
     * - Notification sending
     * - Database persistence
     *
     * @param orderEvent the order event to process
     *
     * @throws IllegalArgumentException if order amount is invalid (less than or equal to 0)
     */
    private void processOrder(OrderEvent orderEvent) {
        // Validate order amount
        if (orderEvent.getAmount() <= 0) {
            throw new IllegalArgumentException(
                String.format("Invalid amount for order %s: %.2f",
                    orderEvent.getOrderId(), orderEvent.getAmount())
            );
        }

        // TODO: Add your business logic here
        // Example:
        // - Update inventory
        // - Process payment
        // - Send notifications
        // - Persist to database

        log.debug("Processing order {} with amount: {}",
            orderEvent.getOrderId(), orderEvent.getAmount());
    }

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000),
            autoCreateTopics = "true",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            dltTopicSuffix = ".DLT"
    )
    @KafkaListener(topics = "order-events", groupId = "notification-group", concurrency = "3")
    public void consumeOrder(OrderEvent orderEvent, Acknowledgment acknowledgment) {
        log.info("consumeOrder called....>>>>");
        if ("TOXIC".equalsIgnoreCase(orderEvent.getStatus())) {
            throw new IllegalArgumentException("Toxic order event: " + orderEvent.getOrderId());
        }

        // Process valid event
        acknowledgment.acknowledge();
    }

    @DltHandler
    public void handleDlt(OrderEvent orderEvent, ConsumerRecord<String, OrderEvent> record) {
        // Persist or alert on failed messages without blocking the main topic

        System.err.printf(
                "Moved order %s to DLT topic %s after retries%n",
                orderEvent.getOrderId(),
                record.topic()
        );
    }
}