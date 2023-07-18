package com.aisc.ngalo.rides

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.AllRideItemBinding
import com.google.firebase.auth.FirebaseAuth

class AllRidesAdapter : RecyclerView.Adapter<AllRidesAdapter.ViewHolder>() {


    private val rides1 = mutableListOf<Ride>()
    fun add(rides: List<Ride>) {
        rides1.addAll(rides)
        notifyDataSetChanged()
    }

    fun clear() {
        rides1.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.all_ride_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = rides1[position]
        holder.bind(item, rides1)
    }

    override fun getItemCount(): Int {
        return rides1.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var auth: FirebaseAuth
        private val binding: AllRideItemBinding = AllRideItemBinding.bind(itemView)
        fun bind(ride: Ride, rides: MutableList<Ride>) {
            auth = FirebaseAuth.getInstance()
            //current user's profile pic
            val uid = FirebaseAuth.getInstance().uid
            binding.distance.text = ride.distance.toString()
            binding.time.text = ride.elapsedTime
            binding.ridename.text = ride.rideName
            binding.username.text = ride.username

        }
    }
}

