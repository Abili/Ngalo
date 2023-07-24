package com.aisc.ngalo.purchases

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aisc.ngalo.databinding.ActivityPurchaseHistoryDetailsBinding
import com.bumptech.glide.Glide

class PurchasesHistoryDetails : AppCompatActivity() {
    lateinit var binding: ActivityPurchaseHistoryDetailsBinding
    private lateinit var purchasesViewModel: PurchasesViewModel
    private val adapter = ItemsPurchasedAdapter()
    var id: String? = null
    var userid: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPurchaseHistoryDetailsBinding.inflate(layoutInflater)
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
        val contact = intent.getStringExtra("contact")
        val desc = intent.getStringExtra("desc")


        Glide.with(binding.root)
            .load(itemImage)
            .into(binding.bikeImage)

        binding.textBikeName.text = itemname
        binding.textViewPrice.text =price

        if (desc!!.isNotEmpty()) {
            binding.textViewDesc.text = desc
        }
        else{
            binding.detailsDesc.visibility = View.GONE
            binding.textViewDesc.visibility = View.GONE
        }


    }
}
