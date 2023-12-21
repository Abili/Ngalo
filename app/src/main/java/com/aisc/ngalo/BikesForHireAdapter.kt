package com.aisc.ngalo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.cart.CartItem
import com.aisc.ngalo.cart.CartViewModel
import com.aisc.ngalo.databinding.BikeItemBinding
import com.aisc.ngalo.models.Bike
import com.aisc.ngalo.models.Category
import com.aisc.ngalo.util.CurrencyUtil
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class BikesForHireAdapter(var viewModel: CartViewModel?) :
    RecyclerView.Adapter<BikesForHireAdapter.ViewHolder>() {
    private var listener: OnCartUpdatedListener? = null
    var onCartItemAddedListener: OnCartItemAddedListener? = null


    interface OnCartUpdatedListener {
        fun onCartUpdated(count: Int)
    }

    interface OnCartItemAddedListener {
        fun onCartItemAdded()
    }

    private val bikes = mutableListOf<Bike>()

    fun add(bike: Bike) {
        bikes.add(bike)
        notifyDataSetChanged()
    }

    fun clear() {
        bikes.clear()
        notifyDataSetChanged()
    }

    fun setOnCartUpdatedListener(listener: OnCartUpdatedListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.bike_item, parent, false)
        return ViewHolder(view, listener).apply {
            onCartItemAddedListener = parent.context as? OnCartItemAddedListener
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playlist = bikes[position]
        holder.bind(playlist)
    }

    override fun getItemCount(): Int {
        return bikes.size
    }


    inner class ViewHolder(
        itemView: View,
        private val listener: OnCartUpdatedListener?
    ) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var auth: FirebaseAuth
        private val binding: BikeItemBinding = BikeItemBinding.bind(itemView)
        fun bind(bike: Bike) {
            val cartItems = mutableListOf<Item>()
            auth = FirebaseAuth.getInstance()
            val priceWithoutCommas = if (bike.price!!.contains(",")) {
                bike.price.replace(",", "").toInt()
            } else {
                bike.price.toInt()
            }
            //current user's profile pic
            if (auth.currentUser!!.photoUrl.toString().isNotEmpty()) {
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
            binding.textViewPrice.text =
                CurrencyUtil.formatCurrency(priceWithoutCommas, "UGX")
            binding.textViewDesc.text = bike.description
            binding.deleteBike.setOnClickListener {
                val hire = FirebaseDatabase.getInstance().reference.child("bikes").child("hire")
                if (FirebaseDatabase.getInstance().reference == hire) {
                    FirebaseDatabase.getInstance().reference.child("bikes").child("hire")
                        .removeValue()
                }

            }
            // Set up click listener for the playlist
            itemView.setOnClickListener {
                // Open the playlist details screen
                val intent = Intent(itemView.context, BikeDetails::class.java)
                intent.putExtra("bikename", bike.name)
                intent.putExtra("bikeprice", bike.price)
                intent.putExtra("bikeimage", bike.imageUrl)
                intent.putExtra("bikedesc", bike.description)
                intent.putExtra("category", bike.category)
                itemView.context.startActivity(intent)
            }
            binding.addToCart.setOnClickListener {
                val activity = itemView.context as AppCompatActivity
                var position = 0
                //val cartItem = activity.findViewById(R.id.cart_count) as TextView
//            cartItems.add(Item(bike.name, bike.price.toInt(), bike.imageUrl))
                viewModel = ViewModelProvider(activity)[CartViewModel::class.java]
                CoroutineScope(Dispatchers.IO).launch {


                    viewModel?.addItem(
                        CartItem(
                            UUID.randomUUID().toString(),
                            bike.name,
                            priceWithoutCommas,
                            bike.imageUrl!!,
                            1,
                            position++,
                            bike.description,
                            Category.ForHire.toString()

                        )
                    )
                    // Wait for the addition to be confirmed
                    delay(1000) // Adjust the delay as needed
                    // Update the UI on the main thread
                    withContext(Dispatchers.Main) {
                        onCartItemAddedListener?.onCartItemAdded()
                    }
                }

                viewModel!!.fetchCartItems().observe(activity) { cartItems ->
                    if (cartItems != null && cartItems.isNotEmpty()) {
                        val itemNames = cartItems.joinToString(separator = "\n") { it.name!! }
                        val itemPrices =
                            cartItems.joinToString(separator = "\n") { it.price.toString() }
                        val items = itemNames.split("\n")
                            .zip(itemPrices.split("\n"))
                            .joinToString(separator = "\n") { (name, price) -> "$name ($price)" }
                        val message = "Added to cart:\n$items"
                        //Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
                    }
                }
                //listener?.onCartUpdated(cartItems.size)
                //cartItem.text = "0"
            }

//            val adminref = FirebaseDatabase.getInstance().reference.child("users")
//            adminref.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    for (adminsnap in snapshot.children) {
//
//                        val category = adminsnap.child("category").getValue(String::class.java)
//                        if (category == "admin") {
//                            binding.deleteBike.visibility = View.VISIBLE
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    //error
//                }
//            })

            binding.deleteBike.setOnClickListener {
                val hire = FirebaseDatabase.getInstance().reference.child("bikes").child("hire")
                hire.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (bikeSnap in snapshot.children) {
                            val pushkey = bikeSnap.key
                            if (pushkey == bike.id) {
                                hire.child(pushkey).removeValue()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }

        }
    }
}
