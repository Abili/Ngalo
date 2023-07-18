package com.aisc.ngalo.rides

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RidesRepository {
    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    private val uidRef = FirebaseDatabase.getInstance().reference.child("users")

    private val databaseRef = FirebaseDatabase.getInstance().reference.child("rides")
    private val usersRidesRef = uidRef.child(uid).child("rides")

    fun getAllRides(onCompletedRequestsLoaded: (List<Ride>) -> Unit) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val completedList = mutableListOf<Ride>()
                    for (childSnapshot in snapshot.children) {
                        val ridename = childSnapshot.child("rideName").getValue(String::class.java)
                        val distance =
                            childSnapshot.child("distance").getValue(Double::class.java)
                        val elapsedTime =
                            childSnapshot.child("elapsedTime").getValue(String::class.java)
                        val rideID = childSnapshot.child("uid").getValue(String::class.java)

                        uidRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (userSnap in snapshot.children) {
                                    val id = userSnap.child("id").getValue(String::class.java)
                                    if(rideID==id) {
                                        val username =
                                            snapshot.child(id!!).child("username").getValue(String::class.java)
                                        val ride = Ride(
                                            uid,
                                            ridename,
                                            distance!!.toFloat(),
                                            elapsedTime!!,
                                            username
                                        )
                                        completedList.clear()
                                        completedList.add(ride)

                                        break // Exit the loop since the user is found
                                    }

                                }
                                // Invoke the callback with the completed list
                                onCompletedRequestsLoaded(completedList)
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
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

    fun getUserRides(onCompletedRequestsLoaded: (List<Ride>) -> Unit) {
        usersRidesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val completedList = mutableListOf<Ride>()
                    for (childSnapshot in snapshot.children) {
                        val ridename = childSnapshot.child("rideName").getValue(String::class.java)
                        val distance =
                            childSnapshot.child("distance").getValue(Double::class.java)
                        val elapsedTime =
                            childSnapshot.child("elapsedTime").getValue(String::class.java)

                        val ride = Ride(
                            uid,
                            ridename!!,
                            distance!!.toFloat(),
                            elapsedTime!!,
                            null
                        )
                        //completedList.clear()
                        completedList.add(ride)
                    }
                    // Invoke the callback with the completed list
                    onCompletedRequestsLoaded(completedList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the onCancelled event if needed
            }
        })

    }
}