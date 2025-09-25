package com.example.orderservice.kafka

import com.example.orderservice.store.OrderStatus
import java.time.OffsetDateTime
import java.util.*

data class OrderStatusUpdateEvent(
    val orderId: UUID,
    val newStatus: OrderStatus,
    val source: String,
    val changedAt: OffsetDateTime = OffsetDateTime.now()
)