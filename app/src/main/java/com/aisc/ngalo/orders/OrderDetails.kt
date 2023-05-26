package com.aisc.ngalo.orders

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.aisc.ngalo.admin.AdminHomePage
import com.aisc.ngalo.databinding.ActivityOrderDetailsBinding
import com.bumptech.glide.Glide

class OrderDetails : AppCompatActivity() {
    lateinit var binding: ActivityOrderDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val orderDetails = intent
        val userName = orderDetails.getStringExtra("username")
        val userImageUrl = orderDetails.getStringExtra("userImageUrl")
        val imageUrl = orderDetails.getStringExtra("orderimage")
        val damgDesc = orderDetails.getStringExtra("description")
        val time = orderDetails.getStringExtra("time")
        val location = orderDetails.getStringExtra("location")
        val id = orderDetails.getStringExtra("id")
        val category = orderDetails.getStringExtra("category")

        Glide.with(binding.root)
            .load(userImageUrl)
            .into(binding.userImage)

        Glide.with(binding.root)
            .load(imageUrl)
            .into(binding.damageImage)


        if (damgDesc.isNullOrEmpty()) {
            binding.description.visibility = View.GONE
            binding.detailsDesc.visibility = View.GONE
        }
        binding.customerName.text = userName
        binding.detailsDesc.text = damgDesc
        binding.customerLocation.text = location
        binding.requestTime.text = time
        binding.category.text = category

        binding.colletBtn.setOnClickListener {
            val intent = Intent(this, AdminHomePage::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

    }
}