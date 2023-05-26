package com.aisc.ngalo.orders

import com.aisc.ngalo.LocationObject
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class OrdersRepository @Inject constructor() {
    val uid = FirebaseAuth.getInstance().uid
    private val uidRef = FirebaseDatabase.getInstance().reference.child("users").child(uid!!)

    private val databaseRef =
        FirebaseDatabase.getInstance().reference.child("users").child(uid!!).child("UsersOrders")

    fun getAllRequests(onCompletedRequestsLoaded: (List<Order>) -> Unit) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val completedList = mutableListOf<Order>()
                    for (childSnapshot in snapshot.children) {
                        val description =
                            childSnapshot.child("description").getValue(String::class.java)
                        val imageUrl = childSnapshot.child("imageUrl").getValue(String::class.java)
                        val latitude = childSnapshot.child("latLng").child("coordinates")
                            .child("latitude").getValue(Double::class.java)
                        val longitude = childSnapshot.child("latLng").child("coordinates")
                            .child("longitude").getValue(Double::class.java)
                        val name =
                            childSnapshot.child("latLng").child("name").getValue(String::class.java)
                        val time = childSnapshot.child("requestTime").getValue(String::class.java)

                        uidRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                    val uidUser = snapshot.child("id").getValue(String::class.java)
                                    if (uidUser != null) {
                                        val userImageUrl =
                                            snapshot.child("imageUrl").getValue(String::class.java)
                                        val userName =
                                            snapshot.child("username").getValue(String::class.java)

                                        val order = Order(
                                            uidUser,
                                            description,
                                            imageUrl,
                                            LocationObject(
                                                LatLng(
                                                    latitude!!,
                                                    longitude!!
                                                ),
                                                name!!
                                            ),
                                            userName!!,
                                            userImageUrl,
                                            time
                                        )

                                        completedList.add(order)
                                    }


                                // Invoke the callback with the completed list
                                onCompletedRequestsLoaded(completedList)
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // Handle the onCancelled event if needed
                            }
                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the onCancelled event if needed

            }
        })
    }

    fun getOrdersCount(callback: (Int) -> Unit) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childsnap in snapshot.children) {
                    val id = childsnap.child("id").getValue(String::class.java)
                    if (id != null) {
                        val orderCount = snapshot.childrenCount.toInt()
                        callback(orderCount)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the onCancelled event if needed
            }
        })
    }
}
