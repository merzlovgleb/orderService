package com.example.orderservice.store

import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

enum class OrderStatus {
    CREATED,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}

data class Cart(
    val id: UUID,
    val clientId: UUID
)

data class CartItem(
    val id: UUID,
    val cart: Cart,
    val productId: String,
    val quantity: Int,
    val price: BigDecimal,
    val currency: String
)

data class OrderEntity(
    val id: UUID,
    val clientId: UUID,
    val cart: Cart,
    val status: OrderStatus,
    val totalAmount: BigDecimal,
    val currency: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class OrderItemEntity(
    val id: UUID,
    val order: OrderEntity,
    val productId: String,
    val quantity: Int,
    val price: BigDecimal,
    val currency: String
)

