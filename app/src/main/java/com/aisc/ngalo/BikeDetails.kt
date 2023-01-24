package com.aisc.ngalo

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.aisc.ngalo.databinding.ActivityBikeDetailsBinding
import com.aisc.ngalo.models.Bike
import com.bumptech.glide.Glide
import java.lang.Integer.parseInt

class BikeDetails : AppCompatActivity() {
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

    }

    companion object {
        val EXTRA_BIKE = Bike::class.java
    }
}