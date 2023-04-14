package com.aisc.ngalo.completed

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.LocationObject
import com.aisc.ngalo.databinding.ActivityCompletedBinding
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CompletedActivity : AppCompatActivity() {
    lateinit var binding: ActivityCompletedBinding
    val adapter = CompletedAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.completedRecycler.adapter = adapter
        binding.completedRecycler.layoutManager = LinearLayoutManager(this)

        val uid = FirebaseAuth.getInstance().uid
        // Replace with your own Firebase Realtime Database reference
        val databaseRef =
            FirebaseDatabase.getInstance().getReference("RepaireRequests").child("completed")

        // Add a value event listener to get the data from all child nodes
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    // Get the values from the child snapshot
                    val description =
                        childSnapshot.child("description").getValue(String::class.java)
                    val imageUrl = childSnapshot.child("imageUrl").getValue(String::class.java)
                    val latitude =
                        childSnapshot.child("location").child("coordinates").child("latitude")
                            .getValue(Double::class.java)
                    val longitude =
                        childSnapshot.child("location").child("coordinates").child("longitude")
                            .getValue(Double::class.java)
                    val name =
                        childSnapshot.child("location").child("name").getValue(String::class.java)

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
                                    Toast.makeText(
                                        this@CompletedActivity,
                                        uidUser,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    //val order = mutableListOf<Order>()
                                    val order =
                                        Completed(
                                            "",
                                            description,
                                            imageUrl,
                                            LocationObject(LatLng(latitude!!, longitude!!), name!!),
                                            userName!!,
                                            userImageUrl,
                                            ""

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
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors
            }
        })

    }
}