package com.aisc.ngalo.admin

import androidx.compose.runtime.State
import com.aisc.ngalo.cart.CartItem

class Purchase(
    val items: List<CartItem>? = null,
    val userLocation: String? = "",
    val paymentMethod: String? = "",
    val pickupLocation: String? = "",
    val coordinates: String?="",
    val trsportfares:Int?=null,
    val grandTotal:Int?=null
)
