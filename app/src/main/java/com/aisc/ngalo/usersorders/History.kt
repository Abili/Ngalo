package com.aisc.ngalo.usersorders

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.databinding.ActivityUsersOrdersBinding
import com.aisc.ngalo.purchases.PurchasesAdapter
import com.aisc.ngalo.purchases.PurchasesViewModel
import com.aisc.ngalo.rides.RidesViewModel
import com.aisc.ngalo.rides.UsersRidesAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class History : AppCompatActivity() {
    lateinit var binding: ActivityUsersOrdersBinding
    val adapter = UsersOrdersAdapter()
    private val purchasesAdapter = PurchasesAdapter()
    private var purchasesViewModel: PurchasesViewModel? = null
    private var ridesViewModel: RidesViewModel? = null
    private val ridesAdapter = UsersRidesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usersOrdersRecycler.adapter = adapter
        binding.usersOrdersRecycler.layoutManager = LinearLayoutManager(this)

        binding.usersPurchasesRecycler.adapter = purchasesAdapter
        binding.usersPurchasesRecycler.layoutManager = LinearLayoutManager(this)

        binding.usersRidesRecycler.adapter = ridesAdapter
        binding.usersRidesRecycler.layoutManager = LinearLayoutManager(this)

        val curUID = FirebaseAuth.getInstance().currentUser!!.uid
        val uid = FirebaseAuth.getInstance().uid
        // Replace with your own Firebase Realtime Database reference
        val databaseRef =
            FirebaseDatabase.getInstance().getReference("users").child(curUID).child("History")

        purchasesViewModel = ViewModelProvider(this)[PurchasesViewModel::class.java]
        purchasesViewModel!!.loadUserPurchasedItems()
        purchasesViewModel!!.purchases.observe(this) {

            if (it.isNotEmpty()) {
                purchasesAdapter.add(it)
                binding.purchasesTag.visibility = View.VISIBLE
                binding.nodata.visibility = View.GONE
            }
        }

        ridesViewModel = ViewModelProvider(this)[RidesViewModel::class.java]
        ridesViewModel!!.loadUserRides()
        ridesViewModel!!.rides.observe(this) {
            if (it.isNotEmpty()) {
                ridesAdapter.add(it)
                binding.ridesTag.visibility = View.VISIBLE
                binding.nodata.visibility = View.GONE
            }
        }
        purchasesViewModel = ViewModelProvider(this)[PurchasesViewModel::class.java]
        purchasesViewModel!!.loadUserOrders(curUID)
        purchasesViewModel!!.userOrders.observe(this) { orders ->
            if (orders.isNotEmpty()) {
                adapter.clear()
                adapter.add(orders)
                binding.ordersTag.visibility = View.VISIBLE
                binding.nodata.visibility = View.GONE
            }
        }
    }

}
