package com.aisc.ngalo.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cartRepository: CartRepository = CartRepository()
    private val _cartItems = MutableLiveData<List<CartItem>>()

    val uid = FirebaseAuth.getInstance().currentUser!!.uid
//    val cartItems: LiveData<List<CartItem>>
//        get() = _cartItems

    init {
        // Initially load local cart data
        _cartItems.value = cartRepository.getItemsFromFirebase().value

    }

    // Observe local cart data changes
    fun fetchCartItems(): LiveData<List<CartItem>> {
        return cartRepository.getItemsFromFirebase()
    }

    // Add item to local cart data and Firebase
    fun addItem(item: CartItem) {
        val cartItem =
            CartItem(item.id, item.name, item.price, item.imageUrl, item.quantity, item.position)
//        cartRepository.addItemOrUpdateQuantity(cartItem)

        cartRepository.addItemsToFirebase(cartItem)
    }

    // Delete item from local cart data and Firebase
    suspend fun deleteItem(item: CartItem) {
        cartRepository.deleteItem(item)
        // TODO: Delete item from Firebase
    }

    private val cartItemsCountLiveData = MutableLiveData<Int>()
    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int>
        get() = _totalPrice

    // Function to get the number of items in the cart
    fun getCartItemsCount() {
        cartRepository.getCartItemsCount { count ->
            // Update the LiveData with the count
            cartItemsCountLiveData.postValue(count)
        }
    }

    // Function to observe the LiveData for the number of items in the cart
    fun observeCartItemsCount(): LiveData<Int> {
        return cartItemsCountLiveData
    }


    fun getTotal(): LiveData<Int> {
        return cartRepository.getTotalPriceOfCartItems()
    }
}

// Get items from Firebase and update local cart data
//    fun syncCart() {
//        val valueEventListener = cartRepository.getItemsFromFirebase()
//        val cartItems = mutableListOf<CartItem>()
//        valueEventListener.let {
//            it.onDataChange { snapshot ->
//                cartItems.clear()
//                for (itemSnapshot in snapshot.children) {
//                    val cartItem = itemSnapshot.getValue(CartItem::class.java)
//                    cartItem?.let {
//                        cartItems.add(it)
//                    }
//                }
//                cartRepository.syncCart(cartItems)
//            }
//        }
//    }

