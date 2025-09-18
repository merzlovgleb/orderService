package com.example.orderservice.store

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class OrderRepository {
    private val orders = mutableMapOf<UUID, OrderEntity>()
    fun save(order: OrderEntity): OrderEntity { orders[order.id] = order; return order }
    fun findById(id: UUID): OrderEntity? = orders[id]
}