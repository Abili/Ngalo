package com.aisc.ngalo.cart

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.admin.ConfirmationActivity
import com.aisc.ngalo.databinding.ActivityCartBinding
import com.google.firebase.auth.FirebaseAuth

class CartActivity : AppCompatActivity() {

    private lateinit var viewModel: CartViewModel
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
        adapter = CartAdapter()
        binding.cartRecycler.layoutManager = LinearLayoutManager(this)


        viewModel.fetchCartItems().observe(this) {
            adapter.add(it)
        }
        binding.cartRecycler.adapter = adapter

        //     Update the total price
        viewModel.getTotal().observe(this) {
            binding.checkout.text = "Check Out (UGX $it)"
        }
        binding.checkout.setOnClickListener {
            var totalprice = 0
            viewModel.getTotal().observe(this) {
                totalprice = it
            }
            val intent = Intent(this, ConfirmationActivity::class.java)
            intent.putExtra("totalprice", totalprice)
            startActivity(intent)
        }

    }

    companion object {
        const val TAG = "CartActivity"
    }

}


