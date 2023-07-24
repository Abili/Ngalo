package com.aisc.ngalo.reciept

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aisc.ngalo.databinding.ActivityReceiptDetailsBinding
import com.bumptech.glide.Glide

class RecieptDetails : AppCompatActivity() {
    lateinit var binding: ActivityReceiptDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityReceiptDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recieptUrl = intent.getStringExtra("receiptUrl")

        Glide.with(binding.root)
            .load(recieptUrl)
            .into(binding.recieptDetail)

        Toast.makeText(this, recieptUrl, Toast.LENGTH_SHORT).show()
    }

}
