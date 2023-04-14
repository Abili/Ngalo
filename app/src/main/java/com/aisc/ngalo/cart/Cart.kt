package com.aisc.ngalo.cart

import com.aisc.ngalo.Item

class Cart {
    private val items: MutableList<Item> = mutableListOf()
    private var itemCount: Int = 0

    fun addItem(item: Item) {
        items.add(item)
        itemCount++
    }


    fun getTotalPrice(): Double {
        return items.sumByDouble { it.price!!.toDouble() }
    }

    fun clearCart() {
        items.clear()
        itemCount = 0
    }

    fun getCartItems(): List<Item> {
        return items.toList()
    }

    fun getItemCount(): Int {
        return itemCount
    }
}
