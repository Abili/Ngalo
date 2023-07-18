package com.aisc.ngalo.purchases

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.FragmentPurchasesBinding

class Purchases : AppCompatActivity() {

    private var _binding: FragmentPurchasesBinding? = null
    private val binding get() = _binding!!
    private lateinit var purchasesViewModel: PurchasesViewModel
    private val adapter = PurchasesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentPurchasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        purchasesViewModel = ViewModelProvider(this)[PurchasesViewModel::class.java]
        purchasesViewModel.loadPurchasedItems()
        purchasesViewModel.purchases.observe(this) { requests ->
            if (requests.isNotEmpty()) {
                adapter.add(requests)
            } else {
                binding.nodataTv.text = getString(R.string.no_orders_available)
                binding.purchasesRecycler.visibility = View.GONE
                binding.nodataLayout.visibility = View.VISIBLE
            }
        }

        binding.purchasesRecycler.adapter = adapter
        binding.purchasesRecycler.layoutManager = LinearLayoutManager(this)
    }
}