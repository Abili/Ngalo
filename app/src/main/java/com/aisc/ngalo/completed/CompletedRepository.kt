package com.aisc.ngalo.completed

import com.aisc.ngalo.LocationObject
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class CompletedRepository @Inject constructor() {
    private val uidRef = FirebaseDatabase.getInstance().reference.child("users")
    private val databaseRef =
        FirebaseDatabase.getInstance().getReference("RepaireRequests").child("completed")

    fun getCompletedRequests(uid: String, onCompletedRequestsLoaded: (List<Completed>) -> Unit) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val completedList = mutableListOf<Completed>()

                for (childSnapshot in snapshot.children) {
                    val description =
                        childSnapshot.child("description").getValue(String::class.java)
                    val imageUrl = childSnapshot.child("imageUrl").getValue(String::class.java)
                    val latitude = childSnapshot.child("location").child("coordinates")
                        .child("latitude").getValue(Double::class.java)
                    val longitude = childSnapshot.child("location").child("coordinates")
                        .child("longitude").getValue(Double::class.java)
                    val name =
                        childSnapshot.child("location").child("name").getValue(String::class.java)
                    val time = childSnapshot.child("timeOfOrder").getValue(String::class.java)

                    uidRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (usersnap in snapshot.children) {
                                val uidUser = usersnap.child("id").getValue(String::class.java)
                                if (uid == uidUser) {
                                    val userImageUrl =
                                        usersnap.child("imageUrl").getValue(String::class.java)
                                    val userName =
                                        usersnap.child("username").getValue(String::class.java)

                                    val order = Completed(
                                        uidUser,
                                        description,
                                        imageUrl,
                                        LocationObject(LatLng(latitude!!, longitude!!), name!!),
                                        userName!!,
                                        userImageUrl,
                                        time
                                    )

                                    completedList.add(order)
                                }
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

            override fun onCancelled(error: DatabaseError) {
                // Handle the onCancelled event if needed
            }
        })
    }
}
