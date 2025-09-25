package com.example.orderservice.kafka

import com.example.orderservice.store.OrderRepository
import com.example.orderservice.store.OrderStatusHistoryEntity
import com.example.orderservice.store.OrderStatusHistoryRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderStatusConsumer(
    private val orderRepository: OrderRepository,
    private val orderStatusHistoryRepository: OrderStatusHistoryRepository
) {

    @KafkaListener(topics = ["order-status-topic"], groupId = "order-service")
    fun consumeStatusUpdate(event: OrderStatusUpdateEvent) {
        val order = orderRepository.findById(event.orderId)
            ?: throw IllegalArgumentException("Order not found: ${event.orderId}")

        val oldStatus = order.status
        val newStatus = event.newStatus

        order.status = newStatus
        order.updatedAt = event.changedAt
        orderRepository.save(order)

        val history = OrderStatusHistoryEntity(
            id = UUID.randomUUID(),
            order = order,
            oldStatus = oldStatus,
            newStatus = newStatus,
            source = event.source,
            changedAt = event.changedAt
        )
        orderStatusHistoryRepository.save(history)

        println("Order ${order.id} updated: $oldStatus -> $newStatus")
    }
}
