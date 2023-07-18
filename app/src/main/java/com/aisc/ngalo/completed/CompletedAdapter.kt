package com.aisc.ngalo.completed

import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MenuItem.OnMenuItemClickListener
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.CompletedItemBinding
import com.aisc.ngalo.helpers.TimeConverter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CompletedAdapter : RecyclerView.Adapter<ViewHolder>() {


    private val orders = mutableListOf<Completed>()

    fun add(com: List<Completed>) {
        orders.clear()
        orders.addAll(com)
        notifyDataSetChanged()
    }

    fun clear() {
        orders.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.completed_item, parent, false)
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
    private val binding: CompletedItemBinding = CompletedItemBinding.bind(itemView)
    fun bind(completed: Completed) {
        auth = FirebaseAuth.getInstance()
        //current user's profile pic
        if (auth.currentUser!!.photoUrl.toString().isNotEmpty()) {
            Glide
                .with(binding.root)
                .load(completed.userImageUrl)
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
            .load(completed.imageUrl)
            .centerInside()
            .into(binding.damageImage)
        val uid = FirebaseAuth.getInstance().uid
        binding.customerName.text = completed.userName
        binding.customerLocation.text = completed.location!!.name
        binding.description.text = completed.description

        val (date, timeFormat) = TimeConverter().dateSimpleDateFormatPair(completed)
        val time = timeFormat.format(date)
        binding.requestTime.text = time

        val completedOrder =
            Completed(
                completed.id,
                completed.description,
                completed.imageUrl,
                completed.location,
                completed.userName,
                completed.userImageUrl,
                completed.timeOfOrder
            )
        binding.completedRb.setOnClickListener {
            val popupMenu = PopupMenu(itemView.context, binding.completedRb)
            popupMenu.menuInflater.inflate(R.menu.completed_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener(object : OnMenuItemClickListener,
                PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
//                        R.id.action_reactivate -> {
//                            val hire =
//                                FirebaseDatabase.getInstance().reference.child("RepaireRequests")
//                            hire.push().setValue(completedOrder)
//
//                            Snackbar.make(
//                                binding.root,
//                                "Request Reactivated",
//                                Snackbar.LENGTH_SHORT
//                            ).show()
//                        }

                        R.id.action_completed -> {
                            FirebaseDatabase.getInstance().reference.child("completed")
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        for (comSnap in snapshot.children) {
                                            val id =
                                                comSnap.child("id").getValue(String::class.java)
                                            if (id == completed.id) {
                                                snapshot.ref.removeValue()
                                            }
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }

                                })

//                                    .removeValue().addOnCompleteListener {
//                                        if (it.isComplete) {
//                                            Snackbar.make(
//                                                binding.root,
//                                                "Deleted Successfully",
//                                                Snackbar.LENGTH_SHORT
//                                            ).show()
//                                        }
//                                    }

                        }
                    }
                    return true
                }
            })


            popupMenu.show()

        }


        // Set up click listener for the playlist
        itemView.setOnClickListener {
            // Open the playlist details screen
            val intent = Intent(itemView.context, CompletedDetails::class.java)
            intent.putExtra("username", completed.userName)
            intent.putExtra("description", completed.description)
            intent.putExtra("location", completed.location.name)
            intent.putExtra("coordinates", completed.location.coordinates)
            intent.putExtra("time", time)
            intent.putExtra("userImageUrl", completed.userImageUrl)
            intent.putExtra("orderimage", completed.imageUrl)
            intent.putExtra("id", completed.id)
            itemView.context.startActivity(intent)
        }
    }
}

