package com.example.orderservice.store

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CartRepository {
    private val carts = mutableMapOf<UUID, Cart>()
    fun findById(id: UUID): Cart? = carts[id]
}