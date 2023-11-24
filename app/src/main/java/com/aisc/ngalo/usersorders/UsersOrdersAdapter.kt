package com.aisc.ngalo.usersorders

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.UsersOrdersItemBinding
import com.aisc.ngalo.helpers.TimeConverter
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UsersOrdersAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val orders = mutableListOf<UserOrder>()
    fun add(order: List<UserOrder>) {
        orders.addAll(order)
        notifyDataSetChanged()
    }

    fun clear() {
        orders.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.users_orders_item, parent, false)
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
    private val binding = UsersOrdersItemBinding.bind(itemView)
    fun bind(order: UserOrder) {
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
        //binding.customerName.text = order.userName
        binding.customerLocation.text = order.location!!.name
        binding.description.text = order.description
        binding.category.text = order.category

        val (date, timeFormat) = TimeConverter().dateSimpleDateFormatPair(order)
        binding.requestTime.text = timeFormat.format(date)

        // Set up click listener for the playlist
        itemView.setOnClickListener {
            // Open the playlist details screen
            val intent = Intent(itemView.context, UserOrderDetails::class.java)
            intent.putExtra("username", order.userName)
            intent.putExtra("description", order.description)
            intent.putExtra("location", order.location.name)
            intent.putExtra("coordinates", order.location.coordinates)
            intent.putExtra("time", timeFormat.format(date))
            intent.putExtra("userImageUrl", order.userImageUrl)
            intent.putExtra("orderimage", order.imageUrl)
            intent.putExtra("id", uid)
            intent.putExtra("category", order.category)
            itemView.context.startActivity(intent)
        }
    }


}


