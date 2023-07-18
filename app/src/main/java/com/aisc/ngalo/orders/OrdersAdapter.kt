package com.aisc.ngalo.orders

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.OrdersItemBinding
import com.aisc.ngalo.helpers.TimeConverter
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrdersAdapter : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {
    var listener: OrderClickListener? = null

    val orders = mutableListOf<Order>()

    interface OrderClickListener {
        fun onOrderCompleted(order: Order)
    }

    fun add(order: List<Order>) {
        orders.addAll(order)
        notifyDataSetChanged()
    }

    fun clear() {
        orders.clear()
        notifyDataSetChanged()
    }

//    fun setOnCartUpdatedListener(listener: OrderClickListener) {
//        this.listener = listener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.orders_item, parent, false)
        return ViewHolder(view, listener).apply {
            listener = parent.context as? OrderClickListener
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playlist = orders[position]
        holder.bind(playlist, orders)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    inner class ViewHolder(itemView: View, listener: OrderClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var auth: FirebaseAuth
        private val binding: OrdersItemBinding = OrdersItemBinding.bind(itemView)
        fun bind(order: Order, orders: MutableList<Order>) {
            auth = FirebaseAuth.getInstance()
            //current user's profile pic
            if (auth.currentUser!!.photoUrl.toString().isNotEmpty()) {
                Glide.with(binding.root).load(order.userImageUrl).centerInside()
                    .into(binding.userImage);
            } else {
                Glide.with(binding.root).load(R.drawable.placeholder_with).centerInside()
                    .into(binding.userImage);
            }
            Glide.with(binding.root).load(order.imageUrl).centerInside().into(binding.damageImage)
            val uid = FirebaseAuth.getInstance().uid
            binding.customerName.text = order.userName
            binding.customerLocation.text = order.location!!.name
            binding.description.text = order.description
            binding.category.text = order.category
            val (date, timeFormat) = TimeConverter().dateSimpleDateFormatPair(order)
            val time = timeFormat.format(date)
            binding.requestTime.text = order.timeOfOrder
            val completedOrder = Order(
                order.id,
                order.description,
                order.imageUrl,
                order.location,
                order.userName,
                order.userImageUrl,
                order.timeOfOrder,
                order.category
            )
            binding.completedRb.setOnClickListener {
                if (binding.completedRb.isChecked) {
                    val hire = FirebaseDatabase.getInstance().reference.child("completed")
                    hire.push().setValue(completedOrder)
                    val position = adapterPosition

                    val removeRef =
                        FirebaseDatabase.getInstance().reference.child("RepaireRequests")
                    removeRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                for (childSnap in snapshot.children) {
                                    val key = childSnap.key
                                    val id = childSnap.child("id").getValue(String::class.java)
                                    if (order.id == id) {
                                        childSnap.ref.removeValue()
                                        orders.removeAt(position)
                                        clear()
                                    }
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
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
                intent.putExtra("time", time)
                intent.putExtra("userImageUrl", order.userImageUrl)
                intent.putExtra("orderimage", order.imageUrl)
                intent.putExtra("id", order.id)
                intent.putExtra("category", order.category)
                itemView.context.startActivity(intent)
            }
        }
    }
}

