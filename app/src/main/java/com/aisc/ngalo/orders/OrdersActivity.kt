package com.aisc.ngalo.orders

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.LocationObject
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.OrdersActivityBinding
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrdersActivity : AppCompatActivity() {
    lateinit var binding: OrdersActivityBinding
    val adapter = OrdersAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrdersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ordersRecycler.adapter = adapter
        binding.ordersRecycler.layoutManager = LinearLayoutManager(this)

        val uid = FirebaseAuth.getInstance().uid
        // Replace with your own Firebase Realtime Database reference
        val databaseRef =
            FirebaseDatabase.getInstance().getReference("RepaireRequests").child(uid!!)

        // Add a value event listener to get the data from all child nodes
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        // Get the values from the child snapshot
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

                        val uidRef = FirebaseDatabase.getInstance().reference.child("users")
                        uidRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (usersnap in snapshot.children) {
                                    val uidUser = usersnap.child("id").getValue(String::class.java)
                                    if (uid == uidUser) {
                                        val userImageUrl =
                                            usersnap.child("imageUrl").getValue(String::class.java)
                                        val userName =
                                            usersnap.child("username").getValue(String::class.java)
                                        val time = childSnapshot.child("requestTime")
                                            .getValue(String::class.java)
                                        Toast.makeText(
                                            this@OrdersActivity,
                                            description,
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        //val order = mutableListOf<Order>()
                                        val order =
                                            Order(
                                                uidUser,
                                                description,
                                                imageUrl,
                                                LocationObject(
                                                    LatLng(latitude!!, longitude!!),
                                                    name!!
                                                ),
                                                userName!!,
                                                userImageUrl,
                                                time,
                                                category
                                            )

                                        adapter.add(order)

                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })

//                    if (uid ==)
//                    // Use the values as needed
//                    // For example, you can display them in a text
//                    // view or store them in variables for further processing
//                        binding.textViewDescription.text = description
//                    binding.textViewImageUrl.text = imageUrl
//                    binding.textViewLatitude.text = latitude.toString()
//                    binding.textViewLongitude.text = longitude.toString()
//                    binding.textViewName.text = name

                    }
                } else {
                    binding.nodataTv.text = getString(R.string.no_orders_available)
                    binding.ordersRecycler.visibility = View.GONE
                    binding.nodataLayout.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors
            }
        })

    }
}