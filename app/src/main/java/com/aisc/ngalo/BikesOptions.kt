package com.aisc.ngalo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.aisc.ngalo.cart.CartActivity
import com.aisc.ngalo.cart.CartItem
import com.aisc.ngalo.cart.CartViewModel
import com.aisc.ngalo.databinding.ActivityBikesOptionsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BikesOptions : AppCompatActivity(), BikesAdapter.OnCartItemAddedListener {

    private lateinit var binding: ActivityBikesOptionsBinding
    private var bikesAdapter: BikesAdapter? = null
    private val orders = mutableListOf<CartItem>()
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBikesOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = PagerAdapter(supportFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.tabs.setBackgroundColor(resources.getColor(R.color.white))
        // binding.tabs.setTabTextColors(R.color.ngalo_green,R.color.ngalo_green)
        setSupportActionBar(binding.optionstoolbar)
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        bikesAdapter = BikesAdapter(cartViewModel)
        bikesAdapter!!.onCartItemAddedListener = this

        //val cartCountView = findViewById<TextView>(R.id.cart_count)
        cartViewModel.getCartItemsCount()

        cartViewModel.observeCartItemsCount().observe(this) { count ->
            // Update your UI with the count of items in the cart
            binding.cartCount.text = count.toString()


            binding.cartIcon.setOnClickListener {
                if (count == 0) {
                    Toast.makeText(this@BikesOptions, "Cart is Empty", Toast.LENGTH_SHORT).show()
                } else {
                    startActivity(Intent(this@BikesOptions, CartActivity::class.java))
                    finish()
                }
            }
        }


        bikesAdapter!!.setOnCartUpdatedListener(object : BikesAdapter.OnCartUpdatedListener {
            override fun onCartUpdated(count: Int) {
                // handle cart update
                Toast.makeText(this@BikesOptions, "Item added", Toast.LENGTH_SHORT).show()
            }
        })


        when (intent.getIntExtra("position", 0)) {
            0 -> {
                binding.viewPager.setCurrentItem(0, false)
            }

            1 -> {
                binding.viewPager.setCurrentItem(1, false)
            }

            else -> {
                binding.viewPager.setCurrentItem(2, false)
            }
        }


    }


    override fun onCartItemAdded() {
        val cartItemsCount = cartViewModel.getCartItemsCount()
        runOnUiThread {
            val cartTv = binding.cartCount
            cartTv.text = cartItemsCount.toString()
            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show()

        }
    }
}

class PagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BikeForPurchaseFragment()
            1 -> BikesForHireFragment()
            2 -> BikePartsFragment()
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Buy Bikes"
            1 -> "Hire Bikes"
            2 -> "Bike Parts"
            else -> ""
        }
    }

}