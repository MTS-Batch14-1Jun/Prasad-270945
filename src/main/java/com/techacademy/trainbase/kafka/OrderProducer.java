package com.techacademy.trainbase.kafka;

import com.techacademy.trainbase.dto.OrderEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

   // private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private static final String TOPIC = "order-events";

    private final StreamBridge streamBridge;

    /*@Transactional("kafkaTransactionManager")
    public void sendOrderAsync(OrderEvent orderEvent) {
        CompletableFuture<SendResult<String, OrderEvent>> future =
            kafkaTemplate.send(TOPIC, orderEvent.getOrderId(), orderEvent);

        future.whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("Sent Order=[{}] to partition=[{}] with offset=[{}]",
                    orderEvent.getOrderId(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send Order=[{}] due to : {}", 
                    orderEvent.getOrderId(), exception.getMessage());
            }
        });
    }*/

    @Transactional("kafkaTransactionManager")
    public void sendOrderAsync(OrderEvent orderEvent) {
        log.info("Sending order event::>>>>>> {}", orderEvent);
        streamBridge.send(TOPIC, orderEvent);

    }
}
