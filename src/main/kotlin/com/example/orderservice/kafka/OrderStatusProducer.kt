package com.example.orderservice.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OrderStatusProducer(
    private val kafkaTemplate: KafkaTemplate<String, OrderStatusUpdateEvent>
) {
    fun sendStatusUpdate(event: OrderStatusUpdateEvent) {
        kafkaTemplate.send("order-status-topic", event.orderId.toString(), event)
        println("Sent status update: $event")
    }
}
