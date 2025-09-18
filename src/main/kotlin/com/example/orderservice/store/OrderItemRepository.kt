package com.example.orderservice.store

import org.springframework.stereotype.Repository

@Repository
class OrderItemRepository {
    private val items = mutableListOf<OrderItemEntity>()
    fun saveAll(orderItems: List<OrderItemEntity>) { items.addAll(orderItems) }
    fun findByOrder(order: OrderEntity): List<OrderItemEntity> = items.filter { it.order.id == order.id }
}