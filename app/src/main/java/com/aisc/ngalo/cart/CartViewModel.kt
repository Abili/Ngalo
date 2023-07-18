package com.aisc.ngalo.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aisc.ngalo.helpers.LocationHelper
import com.google.firebase.auth.FirebaseAuth

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cartRepository: CartRepository = CartRepository()
    //private val locationRepository: LocationRepository = LocationRepository(this.getApplication())
    private val _cartItems = MutableLiveData<List<CartItem>>()
    private val locationHelper = LocationHelper(this.getApplication())

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

    fun getCurrentLocation(): LiveData<String> {
        return cartRepository.setCurrentLocation(this.getApplication())
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


    fun saveUserLocation() {
        locationHelper.getCurrentLocation { location ->
            if (location != null) {
                cartRepository.saveUserLocation(location)
            }
        }
    }


//    fun updateCurrentLocationName(application: Application): LiveData<String> {
//        val currentLocationName = MutableLiveData<String>()
//        viewModelScope.launch {
//            if (ActivityCompat.checkSelfPermission(
//                    application,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                currentLocationName.value = locationRepository.getCurrentLocationName()
//            } else {
//                // Request the location permission and get the current location name
//                // using the repository
//                ActivityCompat.requestPermissions(
//                    application.applicationContext as Activity,
//                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                    LOCATION_PERMISSION_REQUEST_CODE
//                )
//                currentLocationName.value = locationRepository.getCurrentLocationName()
//            }
//        }
//        return currentLocationName
//    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
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

