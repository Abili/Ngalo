package com.aisc.ngalo.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {


    //private val locationRepository: LocationRepository = LocationRepository(this.getApplication())
    private val _cartItems = MutableLiveData<List<CartItem>>()
    //private val locationHelper = LocationHelper(this)
//    val localCartItems: LiveData<List<CartItem>> = liveData {
//        emitSource(cartRepository.getLocalCartItems())
//    }

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

//    fun getCurrentLocation(): LiveData<String> {
//        return cartRepository.setCurrentLocation(this.getApplication())
//    }

    // Add item to local cart data and Firebase
    fun addItem(item: CartItem) {
        val cartItem =
            CartItem(
                item.id,
                item.name,
                item.price,
                item.imageUrl,
                item.quantity,
                item.position,
                item.description,
                item.category
            )
//        cartRepository.addItemOrUpdateQuantity(cartItem)
        viewModelScope.launch {
            //cartRepository.addItemToLocalDatabase(cartItem)
            cartRepository.addItemsToFirebase(cartItem)
        }
    }

    // Delete item from local cart data and Firebase
//    suspend fun deleteItem(item: CartItem) {
//        cartRepository.deleteItem(item)
//    }

    private val cartItemsCountLiveData = MutableLiveData<Int>()
    private val _totalPrice = MutableLiveData<Int>()
//    val totalPrice: LiveData<Int>
//        get() = _totalPrice

    // Function to get the number of items in the cart
    fun getCartItemsCount() {
        cartRepository.getCartItemsCount { count ->
            // Update the LiveData with the count
            cartItemsCountLiveData.value = count
        }
    }

    // Function to observe the LiveData for the number of items in the cart
    fun observeCartItemsCount(): LiveData<Int> {
        return cartItemsCountLiveData
    }


    fun getTotal(): LiveData<Int> {
        return cartRepository.getTotalPriceOfCartItems()
    }




    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


}



