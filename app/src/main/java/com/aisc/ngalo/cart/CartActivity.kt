package com.aisc.ngalo.cart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.Item
import com.aisc.ngalo.databinding.ActivityCartBinding


class CartActivity : AppCompatActivity() {

    var adapter: CartAdapter? = null
    var numberOfItems: Int? = null
    lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CartAdapter(object : CartAdapter.onClick {
            override fun itemOnclick() {
                Toast.makeText(
                    this@CartActivity,
                    "Item clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val image = intent.getStringExtra("image")
        val count = intent.getStringExtra("count")

        adapter!!.add(Item(name, price!!.toInt(), image))
        binding.cartRecycler.adapter = adapter
        binding.cartRecycler.layoutManager = LinearLayoutManager(this)
        adapter!!.notifyDataSetChanged()

        numberOfItems = adapter!!.itemCount
//        val totalPrice = price.toInt() * count!!.toInt()
//        val grandTotal = numberOfItems!! * totalPrice
       // binding.text = grandTotal.toString()


    }


//    override fun itemOnclick() {
//        Toast.makeText(
//            this,
//            "Item Deleted",
//            Toast.LENGTH_SHORT
//        ).show()
//        adapter?.removeSelectedItems()
//        numberOfItems = adapter?.itemCount
//        if (numberOfItems == 0) {
//            binding.totalprice.visibility = View.GONE
//            binding.checkOutBtn.visibility = View.GONE
//        } else {
//            val totalPrice = adapter?.getTotalPrice()
//            val grandTotal = numberOfItems!! * totalPrice!!
//            binding.totalprice.text = grandTotal.toString()
//        }
//    }

}


