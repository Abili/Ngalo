package com.aisc.ngalo.usersorders

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aisc.ngalo.databinding.ActivityUsersOrdersDetailsBinding
import com.bumptech.glide.Glide

class UserOrderDetails : AppCompatActivity() {
    lateinit var binding: ActivityUsersOrdersDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUsersOrdersDetailsBinding.inflate(layoutInflater)
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

        binding.tbCategory.text = category
        binding.detailsDesc.text = damgDesc
        binding.customerLocation.text = location
        binding.requestTime.text = time
        binding.category.text = category


    }
}