package com.aisc.ngalo.usersorders

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.LocationObject
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.ActivityUsersOrdersBinding
import com.aisc.ngalo.purchases.PurchasesAdapter
import com.aisc.ngalo.purchases.PurchasesViewModel
import com.aisc.ngalo.rides.RidesViewModel
import com.aisc.ngalo.rides.UsersRidesAdapter
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsersOrders : AppCompatActivity() {
    lateinit var binding: ActivityUsersOrdersBinding
    val adapter = UsersOrdersAdapter()
    private val purchasesAdapter = PurchasesAdapter()
    private var purchasesViewModel: PurchasesViewModel? = null
    var ridesViewModel: RidesViewModel? = null
    private val ridesAdapter = UsersRidesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usersOrdersRecycler.adapter = adapter
        binding.usersPurchasesRecycler.adapter = purchasesAdapter
        binding.usersOrdersRecycler.layoutManager = LinearLayoutManager(this)
        binding.usersPurchasesRecycler.layoutManager = LinearLayoutManager(this)

        binding.usersRidesRecycler.adapter = ridesAdapter
        binding.usersRidesRecycler.layoutManager = LinearLayoutManager(this)

        val curUID = FirebaseAuth.getInstance().currentUser!!.uid
        val uid = FirebaseAuth.getInstance().uid
        // Replace with your own Firebase Realtime Database reference
        val databaseRef =
            FirebaseDatabase.getInstance().getReference("users")
                .child(curUID).child("UsersOrders")

        purchasesViewModel = ViewModelProvider(this)[PurchasesViewModel::class.java]
        purchasesViewModel!!.loadUserPurchasedItems()
        purchasesViewModel!!.purchases.observe(this) {
            if (it.isNotEmpty()) {
                purchasesAdapter.add(it)
            } else {
                binding.purchasesTag.visibility = View.GONE
            }
        }

        ridesViewModel = ViewModelProvider(this)[RidesViewModel::class.java]
        ridesViewModel!!.loadUserRides()
        ridesViewModel!!.rides.observe(this) {
            if (it.isNotEmpty()) {
                ridesAdapter.add(it)
            } else {
                binding.ridesTag.visibility = View.GONE
            }
        }


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
                        val uidRef =
                            FirebaseDatabase.getInstance().reference.child("users").child(curUID)
                        uidRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val uidUser = snapshot.child("id").getValue(String::class.java)
                                val userImageUrl =
                                    snapshot.child("imageUrl").getValue(String::class.java)
                                val userName =
                                    snapshot.child("username").getValue(String::class.java)
                                val time = childSnapshot.child("requestTime")
                                    .getValue(String::class.java)
                                Toast.makeText(this@UsersOrders, description, Toast.LENGTH_SHORT)
                                    .show()
                                //val order = mutableListOf<Order>()
                                val order =
                                    UserOrder(
                                        uidUser!!,
                                        description,
                                        imageUrl,
                                        LocationObject(LatLng(latitude!!, longitude!!), name!!),
                                        userName!!,
                                        userImageUrl,
                                        time,
                                        category
                                    )

                                adapter.add(order)

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
                    binding.nodataLayout.visibility = View.VISIBLE
                    binding.usersOrdersRecycler.visibility = View.GONE
                    binding.nodataTv.text = getString(R.string.no_orders_made_yet)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors
            }
        })

    }
}