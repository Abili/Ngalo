package com.aisc.ngalo.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AddToCartViewModel
@Inject constructor(private val repository: CartRepository) : ViewModel() {

    private val mTeaSaved = MutableLiveData<Boolean>()

    var num: Int = 0


    fun isSaved(): LiveData<Boolean?> {
        return mTeaSaved
    }

    fun addToCart(
        id: String,
        itemAmount: Int,
        name: String,
        price: Int,
        imageUrl: String,
        position: Int
    ) {
        if (itemAmount == 0) {
            mTeaSaved.value = false
            return
        }
        val cartItem = CartItem(id, name, price, imageUrl, itemAmount, position)
        addToCart(cartItem)
        mTeaSaved.value = true
    }

    private fun addToCart(cartItem: CartItem) {
        return repository.addItemsToFirebase(cartItem)
    }

    fun add() {
        num++
    }


    fun sub() {
        if (num > 1) {
            num--
        }
    }

    fun countTotal(): Int {
        return num
    }
}