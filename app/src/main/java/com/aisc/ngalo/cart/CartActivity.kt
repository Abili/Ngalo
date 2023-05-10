package com.aisc.ngalo.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
        //binding.cartRecycler.adapter = adapter
        binding.cartRecycler.layoutManager = LinearLayoutManager(this)

//        val cartRef = FirebaseDatabase.getInstance().reference.child("cartitems").child(uid)
//        cartRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (cartsnap in snapshot.children) {
//                    val cartItem = cartsnap.getValue(CartItem::class.java)
//                    //val cartItem = CartItem(null, name, price!!.toInt(), null, null)
//                    adapter.add(cartItem!!)
//
//                }
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })

        viewModel.fetchCartItems().observe(this) {
            adapter.add(it)
            //adapter.notifyDataSetChanged()
        }

        binding.cartRecycler.adapter = adapter


        //     Update the total price
        viewModel.getTotal().observe(this){
            binding.checkout.text = "Check Out ($it)"
        }

    }

    companion object {
        const val TAG = "CartActivity"
    }


//     Update the total price
//        val totalPrice = viewModel.getAllItems().value!!.sumOf { it.price * count }
//        binding.totalPrice.text = totalPrice.toString()

}


