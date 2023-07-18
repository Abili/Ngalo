package com.aisc.ngalo.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
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
                    TODO("Not yet implemented")
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
                    orders.removeAt(position)
                    notifyItemRemoved(position)

                    //remove item from firebase as well
                    FirebaseDatabase.getInstance().reference.child("cartitem").child(uid)
                        .child(position.toString()).removeValue()

                    Snackbar.make(binding.root, "Item deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            orders.add(position, deletedItem)
                            notifyItemInserted(position)

                            //restore deleted item into firebase
                            FirebaseDatabase.getInstance().reference.child("cartitem").child(uid)
                                .child(position.toString()).push().setValue(deletedItem)

                            Toast.makeText(itemView.context, " item restored", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .show()
                }


            }


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
                    TODO("Not yet implemented")
                }
            })
        }


    }


}

