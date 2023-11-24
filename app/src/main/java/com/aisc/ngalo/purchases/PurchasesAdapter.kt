package com.aisc.ngalo.purchases

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.PurchaseItemBinding
import com.aisc.ngalo.util.CurrencyUtil
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class PurchasesAdapter : RecyclerView.Adapter<PurchasesAdapter.ViewHolder>() {


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
        val view = layoutInflater.inflate(R.layout.purchase_item, parent, false)
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
        private val binding: PurchaseItemBinding = PurchaseItemBinding.bind(itemView)
        fun bind(order: PurchaseItem, orders: MutableList<PurchaseItem>) {
            auth = FirebaseAuth.getInstance()
            //current user's profile pic
            if (auth.currentUser!!.photoUrl.toString().isNotEmpty()) {
                Glide
                    .with(binding.root)
                    .load(order.purchase_imageUrl)
                    .centerInside()
                    .into(binding.purchaseImage);
            } else {
                Glide
                    .with(binding.root)
                    .load(R.drawable.placeholder_with)
                    .centerInside()
                    .into(binding.purchaseImage);
            }
            val uid = FirebaseAuth.getInstance().uid
            val price = CurrencyUtil.formatCurrency(order.purchase_price!!.toInt(), "UGX")

            binding.purchasePrice.text = price
            binding.purchaseName.text = order.purchase_name
            binding.quantity.text = order.quantity.toString()
            binding.itemCategory.text = order.category

            // Set up click listener for the playlist
            itemView.setOnClickListener {
                // Open the playlist details screen
                val intent = Intent(itemView.context, PurchasesHistoryDetails::class.java)
                intent.putExtra("id", order.id)
                intent.putExtra("itemname", order.purchase_name)
                intent.putExtra("price", price)
                intent.putExtra("username", order.user_name)
                intent.putExtra("location", order.user_location)
                intent.putExtra("quantity", order.quantity)
                intent.putExtra("itemImage", order.purchase_imageUrl)
                intent.putExtra("time", order.time)
                intent.putExtra("desc", order.description)
                intent.putExtra("category", order.category)
                itemView.context.startActivity(intent)
            }
        }
    }

}
