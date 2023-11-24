package com.aisc.ngalo.purchases

import androidx.lifecycle.MutableLiveData
import com.aisc.ngalo.LocationObject
import com.aisc.ngalo.usersorders.UserOrder
import com.google.android.gms.maps.model.LatLng
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
    val curUID = FirebaseAuth.getInstance().currentUser!!.uid

    private val databaseRef = FirebaseDatabase.getInstance().reference.child("purchases")

    private val databaseHistRef =
        FirebaseDatabase.getInstance().getReference("users")
            .child(curUID).child("History")

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
                                        val desc = itemsnap.child("description")
                                            .getValue(String::class.java)
                                        val category =
                                            itemsnap.child("category").getValue(String::class.java)



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
                                                            null,
                                                            null,
                                                            null,
                                                            null,
                                                            null,
                                                            desc,
                                                            category
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
                                    //errors
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
                                        val desc =
                                            itemsnap.child("description")
                                                .getValue(String::class.java)
                                        val category =
                                            itemsnap.child("category").getValue(String::class.java)

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
                                                        null,
                                                        null,
                                                        null,
                                                        null,
                                                        null,
                                                        desc,
                                                        category
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
                                //error
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

    fun recieptItems(time: String, itemsId: String, callback: (List<ItemsPurchased>) -> Unit) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemsPurchasedList = mutableListOf<ItemsPurchased>()
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
                                val desc =
                                    itemSnap.child("description").getValue(String::class.java)
                                val category =
                                    itemSnap.child("category").getValue(String::class.java)


                                val order = ItemsPurchased(
                                    userId,
                                    name,
                                    price,
                                    imageUrl,
                                    grandTotal.toString(),
                                    trsportfares.toString(),
                                    quantity!!.toInt(),
                                    userLocation,
                                    pickupLocation,
                                    ordertime.toString(),
                                    desc,
                                    category
                                )
                                itemsPurchasedList.add(order)
                            }
                        }
                    }
                    // Invoke the callback with the completed list
                    callback(itemsPurchasedList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                callback(emptyList())
            }
        })
    }

    fun items(time: String, itemsId: String, callback: (List<ItemsPurchased>) -> Unit) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemsPurchasedList = mutableListOf<ItemsPurchased>()
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
                                val desc =
                                    itemSnap.child("description").getValue(String::class.java)
                                val category =
                                    itemSnap.child("category").getValue(String::class.java)


                                val order = ItemsPurchased(
                                    userId,
                                    name,
                                    price,
                                    imageUrl,
                                    grandTotal.toString(),
                                    trsportfares.toString(),
                                    quantity!!.toInt(),
                                    userLocation,
                                    pickupLocation,
                                    ordertime.toString(),
                                    desc,
                                    category
                                )
                                itemsPurchasedList.add(order)
                            }
                        }
                    }
                    // Invoke the callback with the completed list
                    callback(itemsPurchasedList)
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
                        val paymentMethod =
                            childSnapshot.child("paymentMethod").getValue(String::class.java)


                        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (usernsnap in snapshot.children) {
                                    val uidUser = usernsnap.child("id").getValue(String::class.java)

                                    if (uidUser == uid) { // Check if the user ID matches
                                        val userImageUrl =
                                            usernsnap.child("imageUrl").getValue(String::class.java)
                                        val userName =
                                            usernsnap.child("username").getValue(String::class.java)
                                        val phoneContact =
                                            usernsnap.child("phone").getValue(String::class.java)


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
                                            time.toString(),
                                            paymentMethod,
                                            phoneContact
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

                if (!snapshot.exists()) {
                    emptyString = "No Purchases Made Yet"

                }

            }

            override fun onCancelled(error: DatabaseError) {
                //error
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
                //error
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
                                //error
                            }

                        })
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the onCancelled event if needed
            }
        })

    }


    fun getUserOrders(uid: String, onOrdersReceived: (List<UserOrder>) -> Unit) {
        val databaseRef =
            FirebaseDatabase.getInstance().getReference("users").child(uid).child("History")

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val orders = mutableListOf<UserOrder>()

                    for (childSnapshot in snapshot.children) {
                        // Your existing logic to extract order details
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
                        val category = childSnapshot.child("category").getValue(String::class.java)

                        userPurRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val uidUser = snapshot.child("id").getValue(String::class.java)
                                val userImageUrl =
                                    snapshot.child("imageUrl").getValue(String::class.java)
                                val userName =
                                    snapshot.child("username").getValue(String::class.java)
                                val time =
                                    childSnapshot.child("requestTime").getValue(String::class.java)
                                // Create UserOrder object
                                val order = UserOrder(
                                    uidUser!!,
                                    description,
                                    imageUrl,
                                    LocationObject(LatLng(latitude!!, longitude!!), name!!),
                                    userName!!,
                                    userImageUrl,
                                    time,
                                    category
                                )

                                orders.add(order)
                                onOrdersReceived(orders)

                            }
                            override fun onCancelled(error: DatabaseError) {
                                //to handlel errors
                            }
                        })
                    }
                }
            }


            override fun onCancelled(error: DatabaseError) {
                //to handlle errors
            }

        })

    }
}











