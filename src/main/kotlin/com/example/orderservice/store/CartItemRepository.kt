package com.example.orderservice.store

import org.springframework.stereotype.Repository

@Repository
class CartItemRepository {
    private val items = mutableListOf<CartItem>()
    fun findByCart(cart: Cart): List<CartItem> = items.filter { it.cart.id == cart.id }
}