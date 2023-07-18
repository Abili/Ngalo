package com.aisc.ngalo.orders

import com.aisc.ngalo.LocationObject
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class OrdersRepository @Inject constructor() {
    val uid = FirebaseAuth.getInstance().uid
    private val uidRef = FirebaseDatabase.getInstance().reference.child("users")

    val uidkey = uidRef.key
    private val databaseRef = FirebaseDatabase.getInstance().reference.child("RepaireRequests")

    fun getAllRepairRequests(onCompletedRequestsLoaded: (List<Order>) -> Unit) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val repairs = mutableListOf<Order>()
                    for (childSnapshot in snapshot.children) {
                        val description =
                            childSnapshot.child("description").getValue(String::class.java)
                        val imageUrl = childSnapshot.child("imageUrl").getValue(String::class.java)
                        val latitude =
                            childSnapshot.child("latLng").child("coordinates").child("latitude")
                                .getValue(Double::class.java)
                        val longitude =
                            childSnapshot.child("latLng").child("coordinates").child("longitude")
                                .getValue(Double::class.java)
                        val name =
                            childSnapshot.child("latLng").child("name").getValue(String::class.java)
                        val time = childSnapshot.child("requestTime").getValue(String::class.java)
                        val id = childSnapshot.child("id").getValue(String::class.java)

                        uidRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (usersnap in snapshot.children) {
                                    val uidUser = usersnap.child("id").getValue(String::class.java)
                                    if (id == uidUser) {
                                        val userImageUrl =
                                            usersnap.child("imageUrl").getValue(String::class.java)
                                        val userName =
                                            usersnap.child("username").getValue(String::class.java)
                                        val order = Order(
                                            uidUser!!, description, imageUrl, LocationObject(
                                                LatLng(
                                                    latitude!!, longitude!!
                                                ), name!!
                                            ), userName!!, userImageUrl, time
                                        )
                                        repairs.clear()
                                        repairs.add(order)

                                        break
                                    }
                                }

                                // Invoke the callback with the completed list
                                onCompletedRequestsLoaded(repairs)

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

    fun deleteOrder(id: String) {
        val deleteRef = FirebaseDatabase.getInstance().getReference("RepaireRequest")
        val completedRef = FirebaseDatabase.getInstance().getReference("completed")

        deleteRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val deletedRepairs = mutableListOf<Order>()
                    val deleteTasks = mutableListOf<Task<Void>>()

                    for (childSnapshot in snapshot.children) {
                        val description = childSnapshot.child("description").getValue(String::class.java)
                        val imageUrl = childSnapshot.child("imageUrl").getValue(String::class.java)
                        val latitude = childSnapshot.child("latLng").child("coordinates")
                            .child("latitude").getValue(Double::class.java)
                        val longitude = childSnapshot.child("latLng").child("coordinates")
                            .child("longitude").getValue(Double::class.java)
                        val name = childSnapshot.child("latLng").child("name").getValue(String::class.java)
                        val time = childSnapshot.child("requestTime").getValue(String::class.java)
                        val userid = childSnapshot.child("id").getValue(String::class.java)
                        val key = childSnapshot.key

                        uidRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (usersnap in snapshot.children) {
                                    val uidUser = usersnap.child("id").getValue(String::class.java)
                                    if (id == uidUser) {
                                        val deleteTask = deleteRef.child(key!!).removeValue()
                                        deleteTasks.add(deleteTask)

                                        val userImageUrl = usersnap.child("imageUrl").getValue(String::class.java)
                                        val userName = usersnap.child("username").getValue(String::class.java)
                                        val order = Order(
                                            uidUser, description, imageUrl, LocationObject(
                                                LatLng(latitude!!, longitude!!), name!!
                                            ), userName!!, userImageUrl, time
                                        )
                                        deletedRepairs.add(order)

                                        break
                                    }
                                }

                                // Check if all deletion tasks are completed
                                if (deleteTasks.size == deletedRepairs.size) {
                                    // All deletion tasks completed, move to "completed" node
                                    for (deletedOrder in deletedRepairs) {
                                        completedRef.push().setValue(deletedOrder)
                                    }
                                }
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
