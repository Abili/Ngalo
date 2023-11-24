package com.aisc.ngalo.cart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.NgaloApplication
import com.aisc.ngalo.admin.ConfirmationActivity
import com.aisc.ngalo.databinding.ActivityCartBinding
import com.aisc.ngalo.util.CurrencyUtil
import com.google.firebase.auth.FirebaseAuth

class CartActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    val uid = FirebaseAuth.getInstance().currentUser!!.uid

    private val cartRepository: CartRepository
        get() = (application as NgaloApplication).cartRepository

    // Access the CartViewModel using the CartRepository
    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory(cartRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewModel = ViewModelProvider(this)[CartViewModel::class.java]
        adapter = CartAdapter()
        binding.cartRecycler.layoutManager = LinearLayoutManager(this)


        viewModel.fetchCartItems().observe(this) {
            adapter.add(it)
        }
        binding.cartRecycler.adapter = adapter

        //     Update the total price
        viewModel.getTotal().observe(this) {
            binding.checkout.text = "Check Out ( ${CurrencyUtil.formatCurrency(it,"UGX")})"
        }
        binding.checkout.setOnClickListener {
            var totalprice = 0
            viewModel.getTotal().observe(this) {
                totalprice = it
            }
            if (adapter.itemCount == 0){
                Toast.makeText(this, "Cart Can not be Empty !!",Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, ConfirmationActivity::class.java)
                intent.putExtra("totalprice", totalprice)
                startActivity(intent)
            }
        }

    }

    companion object {
        const val TAG = "CartActivity"
    }

}


