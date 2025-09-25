package com.example.orderservice.store

import org.springframework.stereotype.Repository

@Repository
class OrderStatusHistoryRepository {
    private val items = mutableListOf<OrderStatusHistoryEntity>()

    fun save(entity: OrderStatusHistoryEntity) {
        items.add(entity)
    }

    fun findByOrder(order: OrderEntity): List<OrderStatusHistoryEntity> =
        items.filter { it.order.id == order.id }
}
