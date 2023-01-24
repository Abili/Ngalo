package com.aisc.ngalo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.databinding.BikeItemBinding
import com.aisc.ngalo.models.Bike
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class BikesAdapter : RecyclerView.Adapter<ViewHolder>() {


    private val bikes = mutableListOf<Bike>()

    fun add(bike: Bike) {
        bikes.add(bike)
        notifyDataSetChanged()
    }

    fun clear() {
        bikes.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.bike_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playlist = bikes[position]
        holder.bind(playlist)
    }

    override fun getItemCount(): Int {
        return bikes.size
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var auth: FirebaseAuth
    private val binding: BikeItemBinding = BikeItemBinding.bind(itemView)
    fun bind(bike: Bike) {
        auth = FirebaseAuth.getInstance()
        //current user's profile pic
        if (!auth.currentUser!!.photoUrl.toString().isEmpty()) {
            Glide
                .with(binding.root)
                .load(bike.imageUrl)
                .centerInside()
                .into(binding.bikeImage);
        } else {
            Glide
                .with(binding.root)
                .load(R.drawable.placeholder_with)
                .centerInside()
                .into(binding.bikeImage);
        }
        binding.textBikeName.text = bike.name
        binding.textViewPrice.text = "Ugx\t${bike.price}"
        binding.textViewDesc.text = bike.description
        binding.deleteBike.setOnClickListener {
            val hire = FirebaseDatabase.getInstance().reference.child("bikes").child("hire")
           if(FirebaseDatabase.getInstance().reference == hire){
               FirebaseDatabase.getInstance().reference.child("bikes").child("hire").removeValue()
           }

        }
        // Set up click listener for the playlist
        itemView.setOnClickListener {
            // Open the playlist details screen
            val intent = Intent(itemView.context, BikeDetails::class.java)
            intent.putExtra("name", bike.name)
            intent.putExtra("price", bike.price)
            intent.putExtra("imageUrl", bike.imageUrl)
            intent.putExtra("desc", bike.description)
            itemView.context.startActivity(intent)
        }
    }
}

