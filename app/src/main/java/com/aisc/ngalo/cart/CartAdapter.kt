package com.aisc.ngalo.cart

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.BikesOptions
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.CartItemBinding
import com.aisc.ngalo.models.items
import com.aisc.ngalo.util.CurrencyUtil
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter() : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val orders = mutableListOf<CartItem>()
    private val cart = mutableListOf<List<CartItem>>()


    fun setItems(items: List<CartItem>) {
        orders.clear()
        orders.addAll(items)
        notifyDataSetChanged()
    }

    fun add(items: List<CartItem>) {
        orders.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        orders.clear()
        notifyDataSetChanged()
    }

    fun removeSelectedItems() {
        items.removeAll(items)
        orders.clear()
        notifyDataSetChanged()
    }

    fun getTotalPrice(): Int {
        var totalPrice = 0
        for (item in items) {
            totalPrice += item.itemPrice.toInt()
        }
        return totalPrice
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = layoutInflater.inflate(R.layout.cart_item, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = orders[position]
        holder.bind(item, orders)
    }

    override fun getItemCount(): Int {
        return orders.size
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var auth: FirebaseAuth
        private val binding: CartItemBinding = CartItemBinding.bind(itemView)


        fun bind(item: CartItem, orders: MutableList<CartItem>) {
            var cartItem = orders.indexOfFirst { it.id == item.id }
            var counter: Int? = null

            var count = item.quantity ?: 0
            auth = FirebaseAuth.getInstance()


            val cartRef = FirebaseDatabase.getInstance().reference.child("cartitems").child(uid)
            cartRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnap in snapshot.children) {
                        val id = cartSnap.child("id").getValue(String::class.java)
                        if (id == item.id.toString()) {
                            counter = cartSnap.child("quantity").getValue(Int::class.java)
                            binding.itemNumber.text = counter.toString()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //to handel errors
                }
            })


            //bike pic
            if (auth.currentUser!!.photoUrl.toString().isNotEmpty()) {
                Glide.with(binding.root)
                    .load(item.imageUrl)
                    .centerInside()
                    .into(binding.cartitemImage)
            } else {
                Glide.with(binding.root)
                    .load(R.drawable.placeholder_with)
                    .centerInside()
                    .into(binding.cartitemImage)
            }

            binding.cartItemName.text = item.name
            binding.cartItemPrice.text = CurrencyUtil.formatCurrency(item.price!!, "UGX")
            binding.itemNumber.text = item.price.toString()
            binding.itemCategory.text = item.category



            binding.cartItemAdd.setOnClickListener {
                count++
                binding.itemNumber.text = count.toString()
                updateCartItemsQuantity(item.id!!, count)
                item.quantity = count
//                itemView.context.startActivity(
//                    Intent(
//                        itemView.context,
//                        CartActivity::class.java
//                    )
//                )
//                binding.itemNumber.text = count.toString()
            }

            binding.cartItemSub.setOnClickListener {
                if (count > 0) {
                    count--
                    binding.itemNumber.text = count.toString()
                    updateCartItemsQuantity(item.id!!, count)
                    item.quantity = count
//                    itemView.context.startActivity(
//                        Intent(
//                            itemView.context,
//                            CartActivity::class.java
//                        )
//                    )
//                    binding.itemNumber.text = count.toString()
                }
            }



            binding.cartRemoveTextview.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val deletedItem = orders[position]

                    // Remove the item from the local list and notify the adapter
                    orders.removeAt(position)
                    notifyItemRemoved(position)

                    // Remove the item from Firebase using its unique identifier (id in this case)
                    cartRef.orderByChild("id").equalTo(deletedItem.id)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (cartSnap in snapshot.children) {
                                    cartSnap.ref.removeValue()
                                    orders.clear()

                                    finishCurrentActivity()

                                    break // Exit the loop once the item is found and removed
                                }

                                // Check if there are no items left in the cart
                                if (!snapshot.hasChildren()) {
                                    // Close the current activity and go back to the previous one
                                    //finishCurrentActivity()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // Handle the error if needed
                            }
                        })

                    // Show a Snackbar with an undo option
                    Snackbar.make(binding.root, "Item deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            // Restore the deleted item locally and notify the adapter
                            orders.add(position, deletedItem)
                            notifyItemInserted(position)

                            // Restore the deleted item to Firebase
                            val newItemRef = cartRef.push()
                            newItemRef.setValue(deletedItem)

                            Toast.makeText(itemView.context, "Item restored", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .show()
                }
            }


        }

        private fun finishCurrentActivity() {
            (itemView.context as? AppCompatActivity)?.finish()
            itemView.context.startActivity(Intent(itemView.context, CartActivity::class.java))

        }

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        private fun updateCartItemsQuantity(itemId: String, quantity: Int) {
            val cartRef = FirebaseDatabase.getInstance().reference.child("cartitems").child(uid)
            cartRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnap in snapshot.children) {
                        val id = cartSnap.child("id").getValue(String::class.java)
                        if (itemId == id) {
                            val key = cartSnap.key
                            cartRef.child(key.toString()).child("quantity").setValue(quantity)
                            orders.clear()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                   //to handlle errors
                }
            })
        }


    }


}

