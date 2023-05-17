package com.aisc.ngalo.cart

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Looper
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aisc.ngalo.Item
import com.aisc.ngalo.LocationObject
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import java.io.IOException
import java.util.Locale

class CartRepository() {
    private var pickupLocation: LocationObject? = null
    var mLastLocation: LocationObject? = null
    private var currentLocation: LocationObject? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    var mLocationRequest: LocationRequest? = null

    private val _cartItems = mutableStateListOf<Item>()
    var cartRef: DatabaseReference? = null
    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("users")
    }

//    fun getAllItems(): LiveData<List<CartItem>> {
//        return cartDao.getAllItems()
//    }
//
//    fun addItemOrUpdateQuantity(item: CartItem) {
//        cartDao.addItemOrUpdateQuantity(item)
//    }


    var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                currentLocation =
                    LocationObject(LatLng(location.latitude, location.longitude), "")

            }
        }
    }


    fun addItemsToFirebase(item: CartItem) {
        cartRef = FirebaseDatabase.getInstance().reference


        val cartItemRef = cartRef!!.child("cartitems").child(uid)
        cartItemRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(currentData: MutableData): Transaction.Result {
                val itemCount = currentData.childrenCount.toInt()
                item.position = itemCount // update position of cartItem
                currentData.child(itemCount.toString()).value = item.toMap()
                return Transaction.success(currentData)
            }

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?
            ) {
                if (error != null || !committed) {
                    // handle error
                } else {
                    // item added successfully
                    // cartRef!!.push().setValue(item)
                }
            }
        })


    }

    fun getItemsFromFirebase(): LiveData<List<CartItem>> {
        val cartLiveData = MutableLiveData<List<CartItem>>()
        cartRef = FirebaseDatabase.getInstance().reference.child("cartitems").child(uid)
        cartRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartItems = mutableListOf<CartItem>()
                for (itemSnapshot in snapshot.children) {
                    val cartItem = itemSnapshot.getValue(CartItem::class.java)
                    cartItem?.let { cartItems.add(it) }
                }
                cartLiveData.postValue(cartItems)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        return cartLiveData
    }

    suspend fun deleteItem(item: CartItem) {
        cartRef = FirebaseDatabase.getInstance().reference.child("cartitems")
        cartRef!!.removeValue()
    }


    fun getCartItemsCount(onComplete: (Int) -> Unit) {
        val cartItemsRef = FirebaseDatabase.getInstance().reference.child("cartitems").child(uid)
        cartItemsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var count = 0
                count += snapshot.childrenCount.toInt()
                onComplete(count)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })
    }

    fun getTotalPriceOfCartItems(): LiveData<Int> {
        val liveData = MutableLiveData<Int>()
        cartRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var totalPrice = 0
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(CartItem::class.java)
                    item?.let {
                        totalPrice += it.price!!.toInt() * it.quantity!!
                    }
                }
                liveData.value = totalPrice
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        return liveData
    }

    fun saveUserLocation(location: Location) {
        val userId = getUserId()
        val userRef = database.child(userId)
        userRef.child("latitude").setValue(location.latitude)
        userRef.child("longitude").setValue(location.longitude)
    }

    private fun getUserId(): String {
        // Return the current user's ID
        return uid
    }


    fun setCurrentLocation(context: Context): LiveData<String> {
        val location = MutableLiveData<String>()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mFusedLocationClient!!.requestLocationUpdates(
                    mLocationRequest!!,
                    mLocationCallback,
                    Looper.myLooper()
                )
            } else {
                checkLocationPermission(context)
            }
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mFusedLocationClient!!.requestLocationUpdates(
                    mLocationRequest!!,
                    mLocationCallback,
                    Looper.myLooper()
                )
            } else {
                checkLocationPermission(context)
            }
        }

        mLastLocation = currentLocation
        pickupLocation =
            LocationObject(
                LatLng(
                    mLastLocation!!.coordinates!!.latitude,
                    mLastLocation!!.coordinates!!.longitude
                ), ""
            )
        //binding.setCurrentLocationTV.setImageDrawable(resources.getDrawable(R.drawable.ic_location_on_primary_24dp))
        pickupLocation = currentLocation
        fetchLocationName(context)
        //binding.requestRepairButton.text = resources.getString(R.string.request_repair)
        location.value = pickupLocation!!.name
        return location
    }

    private fun fetchLocationName(context: Context): LiveData<String> {
        var name = MutableLiveData<String>()
        try {
            val geo = Geocoder(context, Locale.getDefault())
            val addresses = geo.getFromLocation(
                currentLocation!!.coordinates!!.latitude,
                currentLocation!!.coordinates!!.longitude,
                1
            )
            if (addresses!!.isEmpty()) {
                Toast.makeText(context, "Location No Found", Toast.LENGTH_SHORT).show()
            } else {
                addresses.size
                if (addresses[0].thoroughfare == null) {
                    pickupLocation!!.name = addresses[0].locality
                } else if (addresses[0].locality == null) {
                    pickupLocation!!.name = "Unknown Location"
                    name.value = pickupLocation!!.name
                } else {
                    pickupLocation!!.name = addresses[0].locality + ", " + addresses[0].thoroughfare
                    name.value = pickupLocation!!.name
                }
                //binding.userLocationTextView.text = pickupLocation!!.name
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return name
    }

    private fun checkLocationPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(context)
                    .setTitle("give permission")
                    .setMessage("give permission message")
                    .setPositiveButton(
                        "OK"
                    ) { dialogInterface, i ->
                        ActivityCompat.requestPermissions(
                            context,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            1
                        )
                    }
                    .create()
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
        }
    }
}
