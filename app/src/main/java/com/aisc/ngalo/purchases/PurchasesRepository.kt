package com.aisc.ngalo.purchases

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class PurchasesRepository @Inject constructor() {
    val uid = FirebaseAuth.getInstance().uid
    val userid = FirebaseAuth.getInstance().currentUser!!.uid
    private val uidRef = FirebaseDatabase.getInstance().reference.child("users").child(uid!!)
    private val userRef = FirebaseDatabase.getInstance().reference.child("users")
    private val userPurRef = FirebaseDatabase.getInstance().reference.child("users").child(userid)

    private val databaseRef = FirebaseDatabase.getInstance().reference.child("purchases")

    fun getAllPurchases(onCompletedRequestsLoaded: (List<PurchaseItem>) -> Unit) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val completedList = mutableListOf<PurchaseItem>()
                    for (childSnapshot in snapshot.children) {
                        val pickupLocation =
                            childSnapshot.child("pickupLocation").getValue(String::class.java)
                        val userLocation =
                            childSnapshot.child("userLocation").getValue(String::class.java)
                        val uid = childSnapshot.child("userId").getValue(String::class.java)


                        val key = childSnapshot.key
                        databaseRef.child(key!!).child("items")
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (itemsnap in snapshot.children) {
                                        val id = itemsnap.child("id").getValue(String::class.java)
                                        val imageUrl =
                                            itemsnap.child("imageUrl").getValue(String::class.java)
                                        val name =
                                            itemsnap.child("name").getValue(String::class.java)
                                        val price =
                                            itemsnap.child("price").getValue(Int::class.java)
                                        val quantity =
                                            itemsnap.child("quantity").getValue(Long::class.java)

                                        uidRef.addValueEventListener(object : ValueEventListener {
                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                for (usersnap in snapshot.children) {
                                                    val uidUser = usersnap.child("id")
                                                        .getValue(String::class.java)

                                                    if (uid == uidUser) {
                                                        val userImageUrl =
                                                            usersnap.child("imageUrl")
                                                                .getValue(String::class.java)
                                                        val userName = usersnap.child("username")
                                                            .getValue(String::class.java)

                                                        val order = PurchaseItem(
                                                            uidUser,
                                                            userImageUrl,
                                                            name,
                                                            price,
                                                            imageUrl,
                                                            userName,
                                                            userLocation,
                                                            pickupLocation,
                                                            quantity!!.toInt(),

                                                            )
                                                        completedList.clear()
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

    val cuid = FirebaseAuth.getInstance().currentUser!!.uid
    fun getUserPurchases(onCompletedRequestsLoaded: (List<PurchaseItem>) -> Unit) {
        userPurRef.child("purchases").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val completedList = mutableListOf<PurchaseItem>()
                    for (childSnapshot in snapshot.children) {
                        val pickupLocation =
                            childSnapshot.child("pickupLocation").getValue(String::class.java)
                        val userLocation =
                            childSnapshot.child("userLocation").getValue(String::class.java)
                        val uid = childSnapshot.child("userId").getValue(String::class.java)

                        val key = childSnapshot.key
                        userPurRef.child("purchases").child(key!!).child("items")
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (itemsnap in snapshot.children) {
                                        val id = itemsnap.child("id").getValue(String::class.java)
                                        val imageUrl =
                                            itemsnap.child("imageUrl").getValue(String::class.java)
                                        val name =
                                            itemsnap.child("name").getValue(String::class.java)
                                        val price =
                                            itemsnap.child("price").getValue(Int::class.java)
                                        val quantity =
                                            itemsnap.child("quantity").getValue(Long::class.java)

                                        uidRef.addValueEventListener(object : ValueEventListener {
                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                val uidUser = snapshot.child("id")
                                                    .getValue(String::class.java)
                                                if (uid == uidUser) {
                                                    val userImageUrl = snapshot.child("imageUrl")
                                                        .getValue(String::class.java)
                                                    val userName = snapshot.child("username")
                                                        .getValue(String::class.java)

                                                    val order = PurchaseItem(
                                                        uidUser,
                                                        userImageUrl,
                                                        name,
                                                        price,
                                                        imageUrl,
                                                        userName,
                                                        userLocation,
                                                        pickupLocation,
                                                        quantity!!.toInt(),
                                                    )
                                                    completedList.clear()
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

    fun items(time:String, itemsId: String, callback: (List<ItemsPurchased>) -> Unit) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val completedList = mutableListOf<ItemsPurchased>()
                if (snapshot.exists()) {
                    for (purchaseSnap in snapshot.children) {
                        var grandTotal = purchaseSnap.child("grandTotal").getValue(Long::class.java)
                        val paymentMethod =
                            purchaseSnap.child("paymentMethod").getValue(String::class.java)
                        val pickupLocation =
                            purchaseSnap.child("pickupLocation").getValue(String::class.java)
                        val trsportfares =
                            purchaseSnap.child("trsportfares").getValue(Long::class.java)
                        val userId = purchaseSnap.child("userId").getValue(String::class.java)
                        val userLocation =
                            purchaseSnap.child("userLocation").getValue(String::class.java)
                        val ordertime =
                            purchaseSnap.child("time").getValue(Long::class.java)

                        purchaseSnap.child("items").children.forEach { itemSnap ->
                            val id = itemSnap.child("id").getValue(String::class.java)
                            if (userId == itemsId && ordertime.toString() == time) {
                                val imageUrl =
                                    itemSnap.child("imageUrl").getValue(String::class.java)
                                val name = itemSnap.child("name").getValue(String::class.java)
                                val position = itemSnap.child("position").getValue(Long::class.java)
                                val price = itemSnap.child("price").getValue(Int::class.java)
                                val quantity = itemSnap.child("quantity").getValue(Int::class.java)

                                val order = ItemsPurchased(
                                    userId,
                                    name,
                                    price,
                                    imageUrl,
                                    grandTotal.toString(),
                                    trsportfares.toString(),
                                    quantity!!.toInt(),
                                    userLocation,
                                    pickupLocation
                                )
                                completedList.add(order)
                            }
                        }
                    }
                    // Invoke the callback with the completed list
                    callback(completedList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                callback(emptyList())
            }
        })
    }


    fun getUserGroup(onCompletedRequestsLoaded: (List<PurchaseItem>) -> Unit) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userMutableList = mutableListOf<PurchaseItem>()

                    for (childSnapshot in snapshot.children) {
                        val uid = childSnapshot.child("userId").getValue(String::class.java)
                        val pickupLocation =
                            childSnapshot.child("pickupLocation").getValue(String::class.java)
                        val userLocation =
                            childSnapshot.child("userLocation").getValue(String::class.java)
                        val grandtotal =
                            childSnapshot.child("grandTotal").getValue(Long::class.java)
                        val transport =
                            childSnapshot.child("trsportfares").getValue(Long::class.java)
                        val time =
                            childSnapshot.child("time").getValue(Long::class.java)


                        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (usernsnap in snapshot.children) {
                                    val uidUser = usernsnap.child("id").getValue(String::class.java)

                                    if (uidUser == uid) { // Check if the user ID matches
                                        val userImageUrl =
                                            usernsnap.child("imageUrl").getValue(String::class.java)
                                        val userName =
                                            usernsnap.child("username").getValue(String::class.java)

                                        val user = PurchaseItem(
                                            uidUser,
                                            userImageUrl,
                                            null,
                                            null,
                                            null,
                                            userName,
                                            userLocation,
                                            pickupLocation,
                                            null,
                                            grandtotal.toString(),
                                            transport.toString(),
                                            time.toString()
                                        )

                                        if (!userMutableList.contains(user)) { // Check if the user is already in the list
                                            userMutableList.clear()
                                            userMutableList.add(user) // Add the user to the list
                                        }

                                        break // Exit the loop since the user is found
                                    }
                                }

                                // Invoke the callback with the completed list
                                onCompletedRequestsLoaded(userMutableList)
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


    fun emptyPurchase(): String {

        var emptyString: String? = null
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    emptyString = "No Purchases Made Yet"

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return emptyString!!
    }

    fun userId(): String {
        val userid = MutableLiveData<String>()
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (idSnap in snapshot.children) {
                        var id = idSnap.child("id").getValue(String::class.java)
                        id = userid.toString()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return userid.toString()
    }


    fun getPurchaseCount(callback: (Int) -> Unit) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childsnap in snapshot.children) {
                    val key = childsnap.key
                    databaseRef.child(key!!).child("items")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val count = snapshot.childrenCount
                                callback(count.toInt())
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
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




