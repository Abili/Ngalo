package com.aisc.ngalo.orders

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
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
    private var ordersViewModel:OrdersViewModel?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrdersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ordersViewModel = ViewModelProvider(this)[  OrdersViewModel::class.java ]

        ordersViewModel!!.loadCompletedRequests()
        ordersViewModel!!.completedRequests.observe(this){
            if (it.isNotEmpty()){
            adapter.add(it)
            }else{
                binding.nodataTv.text = getString(R.string.no_orders_available)
                binding.ordersRecycler.visibility = View.GONE
                binding.nodataLayout.visibility = View.VISIBLE
            }
        }
        binding.ordersRecycler.adapter = adapter
        binding.ordersRecycler.layoutManager = LinearLayoutManager(this)

    }

}