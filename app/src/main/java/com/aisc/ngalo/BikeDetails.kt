package com.aisc.ngalo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aisc.ngalo.cart.Cart
import com.aisc.ngalo.cart.CartActivity
import com.aisc.ngalo.databinding.ActivityBikeDetailsBinding
import com.aisc.ngalo.models.Bike
import com.bumptech.glide.Glide

class BikeDetails : AppCompatActivity() {
    var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBikeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bike = intent.getParcelableExtra<Bike>("bike")
        if (bike != null) {
            // Set the name, price, and image
            binding.textBikeName.text = bike.name
            binding.textViewPrice.text = "Ugx\t${bike.price}"
            binding.textViewDesc.text = bike.description
            Glide.with(binding.root)
                .load(bike.imageUrl)
                .centerInside()
                .into(binding.bikeImage)
        }

        if (bike!!.description.isNullOrEmpty()) {
            binding.textViewDesc.visibility = View.GONE
            binding.detailsDesc.visibility = View.GONE
        }
        binding.textViewDesc.text = bike.description

        // create a new Cart instance outside of the click listeners
        val cart = Cart()
        val count = binding.countTv!!.text.toString()
        counter = count.toInt()
        binding.addBtn!!.setOnClickListener {
            counter++
            binding.countTv.text = counter.toString()
        }

        binding.subBtn!!.setOnClickListener {
            if (counter > 0) {
                counter--
                binding.countTv.text = counter.toString()
            }
        }


        binding.checkoutBtn!!.setOnClickListener {
            binding.cartValue!!.text = binding.countTv.text.toString()
        }

        binding.cart!!.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("count", binding.cartValue!!.text.toString())
            intent.putExtra("price", bike.price)
            intent.putExtra("name", bike.name)
            intent.putExtra("image", bike.imageUrl)
            startActivity(intent)
        }

    }

    companion object {
        val EXTRA_BIKE = Bike::class.java
    }
}