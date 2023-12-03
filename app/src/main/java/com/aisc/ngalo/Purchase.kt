package com.aisc.ngalo

import com.aisc.ngalo.cart.CartItem


class Purchase(
    val userId: String? = "",
    val items: List<CartItem>? = null,
    val userLocation: String? = "",
    val paymentMethod: String? = "",
    val pickupLocation: String? = "",
    val coordinates: String? = "",
    val trsportfares: Int? = null,
    val grandTotal: Int? = null,
    val time: Long
)
