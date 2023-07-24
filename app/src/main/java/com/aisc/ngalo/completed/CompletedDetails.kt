package com.aisc.ngalo.completed

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aisc.ngalo.databinding.ActivityCompletedDetailsBinding
import com.bumptech.glide.Glide

class CompletedDetails : AppCompatActivity() {
    lateinit var binding: ActivityCompletedDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCompletedDetailsBinding.inflate(layoutInflater)
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


    }
}