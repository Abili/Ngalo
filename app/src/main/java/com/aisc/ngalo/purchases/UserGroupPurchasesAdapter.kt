package com.aisc.ngalo.purchases

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.UserItemBinding
import com.aisc.ngalo.helpers.TimeConverter
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserGroupPurchasesAdapter : RecyclerView.Adapter<UserGroupPurchasesAdapter.ViewHolder>() {


    private val purchases = mutableListOf<PurchaseItem>()
    fun add(purchase: List<PurchaseItem>) {
        purchases.addAll(purchase)
        notifyDataSetChanged()
    }

    fun clear() {
        purchases.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = purchases[position]
        holder.bind(item, purchases)
    }

    override fun getItemCount(): Int {
        return purchases.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var auth: FirebaseAuth
        private val binding: UserItemBinding = UserItemBinding.bind(itemView)
        fun bind(order: PurchaseItem, orders: MutableList<PurchaseItem>) {
            auth = FirebaseAuth.getInstance()
            //current user's profile pic
            if (auth.currentUser!!.photoUrl.toString().isNotEmpty()) {
                Glide.with(binding.root).load(order.userimage).centerInside()
                    .into(binding.usergroupImage);
            } else {
                Glide.with(binding.root).load(R.drawable.placeholder_with).centerInside()
                    .into(binding.usergroupImage);
            }
            val uid = FirebaseAuth.getInstance().uid
            binding.usergroupname.text = "User Name: ${order.user_name}"

            //val price = CurrencyUtil.formatCurrency(order.purchase_price!!.toInt(), "UGX")
            binding.usergroupickuploccation.text = "PickUp Location: ${order.pickup_location}"
            binding.usergrouploccation.text = "User Location: ${order.user_location}"
            binding.gtotal.text = "GrandTotal: ${order.grandTotal}"
            binding.transport.text = "Transport Fares: ${order.transport}"
//        val (date, timeFormat) = TimeConverter().dateSimpleDateFormatPair(order)
            val (date, timeFormat) = TimeConverter().dateSimpleDateFormatPair(order)
            binding.time.text = timeFormat.format(date)
            binding.paymentMethod.text = "Payment Method: ${order.paymentMethod}"


            // Set up click listener for the playlist
            itemView.setOnClickListener {
                // Open the playlist details screen
                val intent = Intent(itemView.context, PurchasesDetails::class.java)
                intent.putExtra("itemname", order.purchase_name)
                intent.putExtra("id", order.id)
                intent.putExtra("price", order.purchase_price)
                intent.putExtra("pickup_location", order.pickup_location)
                intent.putExtra("username", order.user_name)
                intent.putExtra("location", order.user_location)
                intent.putExtra("quantity", order.quantity)
                intent.putExtra("itemImage", order.purchase_imageUrl)
                intent.putExtra("time", order.time)
                intent.putExtra("paymentMethod", order.paymentMethod)
                intent.putExtra("grand", order.grandTotal)
                intent.putExtra("tport", order.transport)
                intent.putExtra("contact", order.contact)
                intent.putExtra("desc", order.description)
                itemView.context.startActivity(intent)
            }

            itemView.setOnLongClickListener { view ->
                val popupMenu = PopupMenu(itemView.context, view)
                popupMenu.inflate(R.menu.completed_menu)

                // Set the menu item click listener
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.action_completed -> {
                            // Handle menu item 1 click
                            val completedPurchase =
                                FirebaseDatabase.getInstance().reference.child("completed")
                                    .child("purchases")
                            completedPurchase.push().setValue(order)

                            val purchases = FirebaseDatabase.getInstance().reference.child("purchases")
                            purchases.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        for (purSnap in snapshot.children) {
                                            val time =
                                                purSnap.child("time").getValue(Long::class.java)
                                            if (time.toString() == order.time) {
                                                purSnap.ref.removeValue()
                                                val position = adapterPosition
                                                clear()
                                                break
                                            }
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            })
                            true
                        }

//                        R.id.action_reactivate -> {
//                            // Handle menu item 2 click
//                            if (!!binding.userlayout.isEnabled) {
//                                binding.userlayout.isEnabled = true
//                            }
//                            true
//                        }
                        // Add more menu items and their corresponding actions

                        else -> false
                    }
                }

                // Show the popup menu
                popupMenu.show()

                true
            }

        }
    }

}
