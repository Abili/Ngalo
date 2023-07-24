package com.aisc.ngalo.purchases

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.databinding.ActivityPurchaseDetailsBinding

class PurchasesDetails : AppCompatActivity() {
    lateinit var binding: ActivityPurchaseDetailsBinding
    private lateinit var purchasesViewModel: PurchasesViewModel
    private val adapter = ItemsPurchasedAdapter()
    var id: String? = null
    var userid: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPurchaseDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        purchasesViewModel = ViewModelProvider(this)[PurchasesViewModel::class.java]
        val userId = intent.getStringExtra("id")
        val gTotal = intent.getStringExtra("grand")
        val tPort = intent.getStringExtra("tport")
        val time = intent.getStringExtra("time")
        val itemname = intent.getStringExtra("itemname")
        val id = intent.getStringExtra("id")
        val price = intent.getStringExtra("price")
        val pickup_location = intent.getStringExtra("pickup_location")
        val username = intent.getStringExtra("username")
        val location = intent.getStringExtra("location")
        val quantity = intent.getStringExtra("quantity")
        val itemImage = intent.getStringExtra("itemImage")



        if (userId != null) {
            purchasesViewModel.loadUserPurchasedItems(time!!,
                userId)
            purchasesViewModel.getPurchasedItems().observe(this) { completedList ->
                adapter.add(completedList)
                Toast.makeText(this, time, Toast.LENGTH_SHORT).show()
            }
            binding.itemspurchasedRecycler.layoutManager = LinearLayoutManager(this)
            binding.itemspurchasedRecycler.adapter = adapter

        } else {
            Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show()
        }


    }
}
