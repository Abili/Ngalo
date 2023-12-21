package com.aisc.ngalo.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener

class CartRepository {

    private var cartRef: DatabaseReference? = null
    val auth = FirebaseAuth.getInstance()
    var uid: String? = null


    fun addItemsToFirebase(item: CartItem) {
        cartRef = FirebaseDatabase.getInstance().reference
        if (auth.currentUser != null) {
            uid = FirebaseAuth.getInstance().currentUser!!.uid

            val cartItemRef = cartRef!!.child("cartitems").child(uid!!)
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
    }

    fun getItemsFromFirebase(): LiveData<List<CartItem>> {
        val cartLiveData = MutableLiveData<List<CartItem>>()
        if (auth.currentUser != null) {
            uid = FirebaseAuth.getInstance().currentUser!!.uid
            cartRef = FirebaseDatabase.getInstance().reference.child("cartitems").child(uid!!)
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

        }
        return cartLiveData
    }


    fun getCartItemsCount(onComplete: (Int) -> Unit) {
        if (auth.currentUser != null) {
            uid = FirebaseAuth.getInstance().currentUser!!.uid
            val cartItemsRef =
                FirebaseDatabase.getInstance().reference.child("cartitems").child(uid!!)
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