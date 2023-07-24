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
import com.aisc.ngalo.admin.UploadItems
import com.aisc.ngalo.cart.CartActivity
import com.aisc.ngalo.cart.CartViewModel
import com.aisc.ngalo.databinding.ActivityBikesOptionsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BikesOptions : AppCompatActivity(), BikesAdapter.OnCartItemAddedListener {

    private lateinit var binding: ActivityBikesOptionsBinding
    private var bikesAdapter: BikesAdapter? = null
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

        }

//        val cartItemsRef = FirebaseDatabase.getInstance().reference.child("cartitems")
//        cartItemsRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var count = 0
//                    count += snapshot.childrenCount.toInt()
//                    binding.cartCount.text = count.toString()
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle the error
//                Toast.makeText(this@BikesOptions, "item not found", Toast.LENGTH_SHORT).show()
//            }
//        })


        binding.cartIcon.setOnClickListener {
            if (binding.cartCount.text == "0") {
                Toast.makeText(this@BikesOptions, "Cart is Empty", Toast.LENGTH_SHORT).show()
            }else{
                startActivity(Intent(this@BikesOptions, CartActivity::class.java))
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        // Get a reference to the custom layout inside the cart menu item
//        val cartMenuItem = menu?.findItem(R.id.action_cart)
//        val cartLayout = cartMenuItem?.actionView
//
//        if (cartLayout == null) {
//            // Handle the case where cartLayout is null
//            Toast.makeText(this@BikesOptions, "Cart layout not found", Toast.LENGTH_SHORT).show()
//        } else {
//            // Get a reference to the TextView inside the custom layout
//            val cartCountTextView = cartLayout.findViewById<TextView>(R.id.cart_count)
//
//            if (cartCountTextView == null) {
//                // Handle the case where cartCountTextView is null
//                Toast.makeText(this@BikesOptions, "Cart count text view not found", Toast.LENGTH_SHORT).show()
//            } else {
//                // Do something with cartCountTextView
//                cartViewModel.getAllItems().observe(this) { cartItems ->
//                    val count = cartItems.sumBy { it.quantity!!.toInt() }
//                    cartCountTextView.text = count.toString()
//                }
//            }
//        }


        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        // Customize the SearchView
        searchView.queryHint = "Search..."

        // Inflate the custom layout for the SearchView
//        val searchBinding = SearchviewBinding.inflate(layoutInflater)
//        searchView.apply {
//            setIconifiedByDefault(false)
//            setContentView(searchBinding.root)
//            // Other customizations for the SearchView
//        }

        val searchResults = ArrayList<String>()
//        val adapter =
//            ArrayAdapter(this, android.R.layout.simple_list_item_1, searchResults)
//        val listView = searchBinding.autoCompleteTv
////        listView.adapter = adapter
//        val layout = LinearLayout(this)
//        layout.orientation = LinearLayout.VERTICAL
//        layout.removeView(listView)
//        layout.addView(searchView)
//        layout.addView(listView)
//        setContentView(layout)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform the search here

                val searchRef =
                    FirebaseDatabase.getInstance().reference.child("bikes")
                searchRef.orderByChild("buy").startAt(query).endAt(query + "\uf8ff")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (postSnapshot in snapshot.children) {
                                // Get the value of your node and use it as needed
                                val result = postSnapshot.child("name")
                                    .getValue(String::class.java)
                                // Add the result to your list or adapter to display in the RecyclerView
                                searchResults.clear()
                                searchResults.add(result!!)

                                // Add items to the searchResults list based on the text entered in the SearchView
                                //adapter.notifyDataSetChanged()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle errors
                        }
                    })

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Update the search results here
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.action_cart -> {
//                startActivity(
//                    Intent(
//                        this@BikesOptions, CartActivity::class.java
//                    )
//                )
//                true
//            }

//            R.id.action_search -> {
//                binding.searchOptions.visibility = View.VISIBLE
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }

    }

//    override fun onCartUpdated(count: Int) {
//        //Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show()
//        val cartTv = findViewById<TextView>(R.id.cart_count)
//        cartTv.text = count.toString()
//    }

//    fun addToCart(view: View) {
//        //Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show()
//
//    }

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