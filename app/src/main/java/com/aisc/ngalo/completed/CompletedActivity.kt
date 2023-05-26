package com.aisc.ngalo.completed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.databinding.ActivityCompletedBinding
import dagger.hilt.android.AndroidEntryPoint


class CompletedActivity : AppCompatActivity() {
    lateinit var completedViewModel: CompletedViewModel

    lateinit var binding: ActivityCompletedBinding
    val adapter = CompletedAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        completedViewModel = ViewModelProvider(this)[CompletedViewModel::class.java]

        binding.completedRecycler.adapter = adapter
        binding.completedRecycler.layoutManager = LinearLayoutManager(this)

        completedViewModel.loadCompletedRequests()
        completedViewModel.completedRequests.observe(this) {
            adapter.add(it)
        }

    }


}