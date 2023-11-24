package com.aisc.ngalo

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aisc.ngalo.cart.CartActivity
import com.aisc.ngalo.cart.CartItem
import com.aisc.ngalo.cart.CartRepository
import com.aisc.ngalo.cart.CartViewModel
import com.aisc.ngalo.cart.CartViewModelFactory
import com.aisc.ngalo.databinding.ActivityBikesOptionsBinding

class BikesOptions : AppCompatActivity(),
    BikesForPurchaseAdapter.OnCartItemAddedListener,
    BikesForHireAdapter.OnCartItemAddedListener,
    BikesPartsAdapter.OnCartItemAddedListener {

    private lateinit var binding: ActivityBikesOptionsBinding
    private var bikesAdapter: BikesAdapter? = null
    private var bikesForPurchaseAdapter: BikesForPurchaseAdapter? = null
    private val orders = mutableListOf<CartItem>()
    private lateinit var cartTextView: TextView
    private val cartRepository: CartRepository
        get() = (application as NgaloApplication).cartRepository

    // Access the CartViewModel using the CartRepository
    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(cartRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBikesOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = PagerAdapter(supportFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.tabs.setBackgroundColor(resources.getColor(R.color.white))
        // binding.tabs.setTabTextColors(R.color.ngalo_green,R.color.ngalo_green)
        setSupportActionBar(binding.optionstoolbar)
        //cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        bikesAdapter = BikesAdapter(cartViewModel)
        bikesForPurchaseAdapter = BikesForPurchaseAdapter(cartViewModel)
        bikesForPurchaseAdapter!!.onCartItemAddedListener = this

        //val cartCountView = findViewById<TextView>(R.id.cart_count)
        cartViewModel.getCartItemsCount()

        // Observe both cart count and cart items
        cartViewModel.observeCartItemsCount().observe(this) { count ->
            // Update your UI with the count of items in the cart
            binding.cartCount.text = count.toString()
            binding.cartIcon.setOnClickListener {
                if (count > 0) {
                    startActivity(Intent(this@BikesOptions, CartActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@BikesOptions, "Cart is Empty", Toast.LENGTH_SHORT).show()

                }
            }
        }

//        // Observe the actual cart items and update the UI accordingly
//        cartViewModel.fetchCartItems().observe(this) { cartItems ->
//            // Perform UI updates based on the cart items
//            // For example, you can update the adapter or any other UI elements
//            if (cartItems != null && cartItems.isNotEmpty()) {
//                // Do something with the cart items
//                // For example, update your adapter with the new list of cart items
//                bikesAdapter?.clear()
//                bikesAdapter?.addAll(cartItems)
//                bikesAdapter?.notifyDataSetChanged()
//            }
//        }

//        bikesAdapter!!.setOnCartUpdatedListener(object : BikesAdapter.OnCartUpdatedListener {
//            override fun onCartUpdated(count: Int) {
//                // Handle cart update
//                binding.cartCount.text = count.toString()
//                Toast.makeText(this@BikesOptions, "Item added", Toast.LENGTH_SHORT).show()
//            }
//        })


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

//    override fun onCartUpdated(count: Int) {
//        runOnUiThread {
//            binding.cartCount.text = count.toString()
//            Toast.makeText(this@BikesOptions, "Item added", Toast.LENGTH_SHORT).show()
//        }
//    }


//    fun onCartItemAdded(size: Int) {
//        runOnUiThread {
//            //cartViewModel.observeCartItemsCount().observe(this) {
//            val cartTv = binding.cartCount
//            cartTv.text = size.toString()
//            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show()
//
//
//        }
//
//    }

    override fun onCartItemAdded() {
        runOnUiThread {
            val cartViewModel = cartViewModel.getCartItemsCount()
            val cartTv = binding.cartCount
            //cartTv.text = cartViewModel.toString()
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
