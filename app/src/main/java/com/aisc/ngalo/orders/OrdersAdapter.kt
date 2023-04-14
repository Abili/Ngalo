package com.aisc.ngalo.orders

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.R
import com.aisc.ngalo.admin.AdminHomePage
import com.aisc.ngalo.databinding.BikeItemBinding
import com.aisc.ngalo.databinding.OrdersItemBinding
import com.aisc.ngalo.models.Bike
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class OrdersAdapter : RecyclerView.Adapter<ViewHolder>() {


    private val orders = mutableListOf<Order>()
    fun add(order: Order) {
        orders.add(order)
        notifyDataSetChanged()
    }

    fun clear() {
        orders.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.orders_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playlist = orders[position]
        holder.bind(playlist)
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var auth: FirebaseAuth
    private val binding: OrdersItemBinding = OrdersItemBinding.bind(itemView)
    fun bind(order: Order) {
        auth = FirebaseAuth.getInstance()
        //current user's profile pic
        if (auth.currentUser!!.photoUrl.toString().isNotEmpty()) {
            Glide
                .with(binding.root)
                .load(order.userImageUrl)
                .centerInside()
                .into(binding.userImage);
        } else {
            Glide
                .with(binding.root)
                .load(R.drawable.placeholder_with)
                .centerInside()
                .into(binding.userImage);
        }
        Glide
            .with(binding.root)
            .load(order.imageUrl)
            .centerInside()
            .into(binding.damageImage)
        val uid = FirebaseAuth.getInstance().uid
        binding.customerName.text = order.userName
        binding.customerLocation.text = order.location!!.name
        binding.description.text = order.description
        binding.category.text = order.category
        val completedOrder =
            Order(
                uid!!,
                order.description,
                order.imageUrl,
                order.location,
                order.userName,
                order.userImageUrl,
                ServerValue.TIMESTAMP.toString(),
                order.category
            )
        binding.completedRb.setOnClickListener {
            if (binding.completedRb.isChecked) {
                val hire =
                    FirebaseDatabase.getInstance().reference.child("RepaireRequests")
                        .child("completed")
                hire.push().setValue(completedOrder)

                if (uid == order.id) {
                    FirebaseDatabase.getInstance().reference.child("RepaireRequests").child(uid)
                }

            }

        }


        // Set up click listener for the playlist
        itemView.setOnClickListener {
            // Open the playlist details screen
            val intent = Intent(itemView.context, OrderDetails::class.java)
            intent.putExtra("username", order.userName)
            intent.putExtra("description", order.description)
            intent.putExtra("location", order.location.name)
            intent.putExtra("coordinates", order.location.coordinates)
            intent.putExtra("time", order.timeOfOrder)
            intent.putExtra("userImageUrl", order.userImageUrl)
            intent.putExtra("orderimage", order.imageUrl)
            intent.putExtra("id", uid)
            intent.putExtra("category", order.category)
            itemView.context.startActivity(intent)
        }
    }
}

