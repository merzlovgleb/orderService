package com.example.orderservice.store

import com.example.orderservice.proto.OrderItem
import com.example.orderservice.proto.OrderResponse

fun OrderEntity.toProto(items: List<OrderItemEntity>): OrderResponse {
    return OrderResponse.newBuilder()
        .setOrderId(id.toString())
        .setStatus(status.name)
        .setTotalAmount(totalAmount.toDouble())
        .setCurrency(currency)
        .addAllItems(items.map { it.toProto() })
        .setCreatedAt(createdAt.toString())
        .setUpdatedAt(updatedAt.toString())
        .build()
}

fun OrderItemEntity.toProto(): OrderItem {
    return OrderItem.newBuilder()
        .setProductId(productId)
        .setQuantity(quantity)
        .setPrice(price.toDouble())
        .setCurrency(currency)
        .build()
}

