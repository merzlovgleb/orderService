package com.example.orderservice.service

import com.example.orderservice.proto.OrderResponse
import com.example.orderservice.store.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class OrderService(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository
) {
    @Transactional
    fun createOrder(cartId: UUID, clientId: UUID, currency: String): OrderResponse {
        val cart = cartRepository.findById(cartId) ?: throw IllegalArgumentException( "Cart not found")
        val cartItems = cartItemRepository.findByCart(cart)

        val total = cartItems.sumOf { it.price.toDouble() * it.quantity }

        val order = orderRepository.save(
            OrderEntity(
                id = UUID.randomUUID(),
                clientId = clientId,
                cart = cart,
                status = OrderStatus.CREATED,
                totalAmount = total.toBigDecimal(),
                currency = currency,
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now()
            )
        )

        val orderItems = cartItems.map {
            OrderItemEntity(
                id = UUID.randomUUID(),
                order = order,
                productId = it.productId,
                quantity = it.quantity,
                price = it.price,
                currency = it.currency
            )
        }
        orderItemRepository.saveAll(orderItems)

        return order.toProto(orderItems)
    }

    fun getOrderById(id: UUID): OrderResponse {
        val order = orderRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Order not found") }
        val items = orderItemRepository.findByOrder(order)
        return order.toProto(items)
    }
}