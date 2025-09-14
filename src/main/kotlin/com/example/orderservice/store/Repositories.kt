package com.example.orderservice.store

import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
class CartRepository {
    private val carts = mutableMapOf<UUID, Cart>()
    fun findById(id: UUID): Cart? = carts[id]
}

@Repository
class CartItemRepository {
    private val items = mutableListOf<CartItem>()
    fun findByCart(cart: Cart): List<CartItem> = items.filter { it.cart.id == cart.id }
}

@Repository
class OrderRepository {
    private val orders = mutableMapOf<UUID, OrderEntity>()
    fun save(order: OrderEntity): OrderEntity { orders[order.id] = order; return order }
    fun findById(id: UUID): Optional<OrderEntity> = Optional.ofNullable(orders[id])
}

@Repository
class OrderItemRepository {
    private val items = mutableListOf<OrderItemEntity>()
    fun saveAll(orderItems: List<OrderItemEntity>) { items.addAll(orderItems) }
    fun findByOrder(order: OrderEntity): List<OrderItemEntity> = items.filter { it.order.id == order.id }
}

