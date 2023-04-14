package com.aisc.ngalo.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.Item
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.CartItemBinding
import com.aisc.ngalo.models.items
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class CartAdapter(private val listener: onClick) : RecyclerView.Adapter<CartAdapter.ViewHolder>(),
    View.OnClickListener {

    private val orders = mutableListOf<Item>()

    interface onClick {
        fun itemOnclick()
    }

    fun add(item: Item) {
        orders.add(item)
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
        val binding = CartItemBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = orders[position]
        holder.bind(item, orders)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onClick(v: View?) {
        listener.itemOnclick()
    }

    inner class ViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var auth: FirebaseAuth

        init {
            binding.root.setOnClickListener {
                listener.itemOnclick()
            }
        }

        fun bind(item: Item, orders: MutableList<Item>) {
            auth = FirebaseAuth.getInstance()

            //bike pic
            if (auth.currentUser!!.photoUrl.toString().isNotEmpty()) {
                Glide.with(binding.root)
                    .load(item.image)
                    .centerInside()
                    .into(binding.cartitemImage)
            } else {
                Glide.with(binding.root)
                    .load(R.drawable.placeholder_with)
                    .centerInside()
                    .into(binding.cartitemImage)
            }

            binding.cartItemName.text = item.name
            binding.cartItemPrice.text = item.price.toString()
            var count = 0

            binding.cartItemAdd.setOnClickListener {
                count++
                binding.itemNumber.text = count.toString()
            }

            binding.cartItemSub.setOnClickListener {
                if (count > 0) {
                    count--
                    binding.itemNumber.text = count.toString()
                }

            }

            binding.cartRemoveTextview.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val deletedItem = orders[position]
                    orders.removeAt(position)
                    notifyItemRemoved(position)
                    Snackbar.make(binding.root, "Item deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            orders.add(position, deletedItem)
                            notifyItemInserted(position)
                            Toast.makeText(itemView.context, " item restored", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .show()
                }


            }

        }
    }


}
