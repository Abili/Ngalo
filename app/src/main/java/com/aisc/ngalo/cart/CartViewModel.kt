package com.aisc.ngalo.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aisc.ngalo.Item
import kotlinx.coroutines.launch



class CartViewModel(private val repository: CartRepository) : ViewModel() {
    // Create a mutable list to hold the cart items
     val cartItems = repository.cartItems

    init {
        viewModelScope.launch {
            cartItems
        }
    }

    // Add an item to the cart
    fun addItem(item: Item) {
        repository.addItemToCart(item)
    }
//
//
//    // Remove an item from the cart
//    fun removeItem(item: Item) {
//        cartItems.remove(item)
//    }
//
//    // Get the current cart items
//    fun getCartItems(): List<Item> {
//        return cartItems
//    }
//
//    // Get the total price of the cart
//    fun getTotalPrice(): Int {
//        var totalPrice = 0
//        for (item in cartItems) {
//            totalPrice += item.price!!
//        }
//        return totalPrice
//    }
}

