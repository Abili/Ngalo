package com.aisc.ngalo.reciept

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aisc.ngalo.databinding.RecieptBinding

class ReceiptsAdapter() : RecyclerView.Adapter<ReceiptsAdapter.ReceiptViewHolder>() {
    private val item = mutableListOf<Reciept>()

    fun add(purchase: List<Reciept>) {
        item.addAll(purchase)
        notifyDataSetChanged()
    }

    fun clear() {
        item.clear()
        notifyDataSetChanged()
    }

    class ReceiptViewHolder(private val binding: RecieptBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Reciept) {
            binding.recieptUsername.text = "Customer Name: ${item.username}"
            binding.date.text = "Date: ${item.date}"
            binding.time.text = "Time: ${item.time}"

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, RecieptDetails::class.java)
                intent.putExtra("receiptUrl", item.recieptUrl)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptViewHolder {
        val binding = RecieptBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReceiptViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        val item = item[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int = item!!.size
}
