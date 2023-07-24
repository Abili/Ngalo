package com.aisc.ngalo.reciept

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.databinding.ActivityRecieptsBinding

class Reciepts : AppCompatActivity() {
    private lateinit var binding: ActivityRecieptsBinding
    private lateinit var receiptsViewModel: ReceiptsViewModel
    lateinit var adapter: ReceiptsAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecieptsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiptsViewModel = ViewModelProvider(this)[ReceiptsViewModel::class.java]
        adapter = ReceiptsAdapter()
        layoutManager = LinearLayoutManager(this)


        binding.recieptList.adapter = adapter
        binding.recieptList.layoutManager = layoutManager

        receiptsViewModel.getDownloadUrl()
        receiptsViewModel.reciept.observe(this) {
            // Use a library like Picasso or Glide to load the image into an ImageView
            adapter.add(it)

        }


    }

}
