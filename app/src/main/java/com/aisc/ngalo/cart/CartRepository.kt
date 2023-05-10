package com.aisc.ngalo.cart

import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aisc.ngalo.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener

class CartRepository() {

    private val _cartItems = mutableStateListOf<Item>()
    var cartRef: DatabaseReference? = null
    val uid = FirebaseAuth.getInstance().currentUser!!.uid

//    fun getAllItems(): LiveData<List<CartItem>> {
//        return cartDao.getAllItems()
//    }
//
//    fun addItemOrUpdateQuantity(item: CartItem) {
//        cartDao.addItemOrUpdateQuantity(item)
//    }

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

}
