package com.techacademy.trainbase.kafka;

import com.techacademy.trainbase.dto.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;


@Service
@Slf4j
@Configuration
public class OrderConsumer {

    private final Executor taskExecutor;



    @Autowired
    public OrderConsumer(@Qualifier("applicationTaskExecutor") Executor taskExecutor) {
        if (taskExecutor == null) {
            throw new IllegalArgumentException("taskExecutor cannot be null");
        }
        this.taskExecutor = taskExecutor;
        log.info("OrderConsumer initialized with executor: {}", taskExecutor.getClass().getSimpleName());
    }


   /* @KafkaListener(
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
    }*/

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

   /* @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000),
            autoCreateTopics = "true",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            dltTopicSuffix = ".DLT"
    )*/
   /* @KafkaListener(topics = "order-events", groupId = "notification-group", concurrency = "3")
    public void consumeOrder(OrderEvent orderEvent, Acknowledgment acknowledgment) {
        log.info("consumeOrder called....>>>>");
        if ("TOXIC".equalsIgnoreCase(orderEvent.getStatus())) {
            throw new IllegalArgumentException("Toxic order event: " + orderEvent.getOrderId());
        }

        // Process valid event
        acknowledgment.acknowledge();
    }*/

    @Bean
    public Consumer<Message<OrderEvent>> consumeOrder() {
        return message -> {
            log.info("consumeOrder called....>>>>");

            OrderEvent orderEvent = message.getPayload();

            // Extract acknowledgment header for manual committing
            Acknowledgment acknowledgment = message.getHeaders()
                    .get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);

            if ("TOXIC".equalsIgnoreCase(orderEvent.getStatus())) {
                throw new IllegalArgumentException("Toxic order event: " + orderEvent.getOrderId());
            }

            // Process valid event here

            // Manually acknowledge the message
            if (acknowledgment != null) {
                acknowledgment.acknowledge();
            }
        };
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
