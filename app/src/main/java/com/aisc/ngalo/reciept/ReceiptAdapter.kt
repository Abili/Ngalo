package com.aisc.ngalo.reciept

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.databinding.RecieptItemBinding
import com.aisc.ngalo.purchases.ItemsPurchased
import com.bumptech.glide.Glide

class ReceiptAdapter() : RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder>() {
    private val item = mutableListOf<ItemsPurchased>()

    fun add(purchase: List<ItemsPurchased>) {
        item.addAll(purchase)
        notifyDataSetChanged()
    }

    fun clear() {
        item.clear()
        notifyDataSetChanged()
    }

    class ReceiptViewHolder(private val binding: RecieptItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsPurchased) {
            Glide.with(itemView.context)
                .load(item.purchase_imageUrl)
                .into(binding.itemImageView)

            binding.textViewItem.text = "Item: ${item.purchase_name}"
            binding.textViewQuantity.text = "Quantity: ${item.quantity}"
            val itemTotal = item.quantity!!.toInt() * item.purchase_price!!.toInt()
            binding.textViewItemTotal.text = "Total: $itemTotal"
            binding.category.text = item.category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptViewHolder {
        val binding = RecieptItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReceiptViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        val item = item[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = item!!.size
}
