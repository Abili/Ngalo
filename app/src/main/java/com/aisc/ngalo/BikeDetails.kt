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

        val bikeDetails = intent
        val bikeName = bikeDetails.getStringExtra("name")
        val bikePrice = bikeDetails.getStringExtra("price")
        val bikeImage = bikeDetails.getStringExtra("imageUrl")
        val bikeDesc = bikeDetails.getStringExtra("desc")

        binding.textBikeName.text = bikeName
        binding.textViewPrice.text = "Ugx\t${bikePrice}"
        Glide.with(this)
            .load(bikeImage)
            .into(binding.bikeImage)

        if (bikeDesc.isNullOrEmpty()) {
            binding.textViewDesc.visibility = View.GONE
            binding.detailsDesc.visibility = View.GONE
        }
        binding.textViewDesc.text = bikeDesc

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
            intent.putExtra("price", bikePrice)
            intent.putExtra("name", bikeName)
            intent.putExtra("image", bikeImage)
            startActivity(intent)
        }

    }

    companion object {
        val EXTRA_BIKE = Bike::class.java
    }
}