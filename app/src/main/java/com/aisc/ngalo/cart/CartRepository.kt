package com.aisc.ngalo.cart

import androidx.compose.runtime.mutableStateListOf
import com.aisc.ngalo.Item

class CartRepository {

    private val _cartItems = mutableStateListOf<Item>()
    val cartItems: List<Item> get() = _cartItems

    fun addItemToCart(item: Item) {
        _cartItems.add(item)
    }
}


