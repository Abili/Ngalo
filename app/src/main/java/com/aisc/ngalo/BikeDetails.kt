package com.aisc.ngalo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aisc.ngalo.cart.CartActivity
import com.aisc.ngalo.cart.CartItem
import com.aisc.ngalo.cart.CartRepository
import com.aisc.ngalo.cart.CartViewModel
import com.aisc.ngalo.cart.CartViewModelFactory
import com.aisc.ngalo.databinding.ActivityBikeDetailsBinding
import com.aisc.ngalo.models.Bike
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class BikeDetails : AppCompatActivity() {
    var counter = 0
    private val orders = mutableListOf<CartItem>()
    lateinit var binding: ActivityBikeDetailsBinding

    private val cartRepository: CartRepository
        get() = (application as NgaloApplication).cartRepository

    // Access the CartViewModel using the CartRepository
    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(cartRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBikeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.backarrow!!.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    BikesOptions::class.java
                )
            )
            finish()
        }
        //cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]


        cartViewModel.getCartItemsCount()
        cartViewModel.observeCartItemsCount().observe(this) { countNum ->
            // Update your UI with the count of items in the cart
            binding.cartValue!!.text = countNum.toString()


            val bikename = intent.getStringExtra("bikename")
            val price = intent.getStringExtra("bikeprice")
            val img = intent.getStringExtra("bikeimage")
            val desc = intent.getStringExtra("bikedesc")
            if (intent != null) {
                // Set the name, price, and image
                binding.textBikeName.text = bikename
                binding.textViewPrice.text = "Ugx\t${price}"
                Glide.with(binding.root)
                    .load(img)
                    .centerInside()
                    .into(binding.bikeImage)

                if (desc.isNullOrEmpty()) {
                    binding.textViewDesc.visibility = View.GONE
                    binding.detailsDesc.visibility = View.GONE
                }
                binding.textViewDesc.text = desc
            }


            // create a new Cart instance outside of the click listeners
            val cart = CartItem()

            binding.checkoutBtn!!.setOnClickListener {
                //binding.cartValue!!.text = binding.countTv!!.text
                updateCartItemsQuantity(cart.id, counter)
                var position = 0
                //val cartItem = activity.findViewById(R.id.cart_count) as TextView
//            cartItems.add(Item(bike.name, bike.price.toInt(), bike.imageUrl))
                //cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
                CoroutineScope(Dispatchers.IO).launch {
                    cartViewModel.addItem(
                        CartItem(
                            UUID.randomUUID().toString(),
                            bikename,
                            price!!.toInt(),
                            img!!,
                            1,
                            position++,
                            desc

                        )
                    )
                    // Wait for the addition to be confirmed
                    delay(1000) // Adjust the delay as needed
                    // Update the UI on the main thread
                    withContext(Dispatchers.Main) {
                        onCartItemAdded()
                    }
                }

                cartViewModel.fetchCartItems().observe(this) { cartItems ->
                    if (cartItems != null && cartItems.isNotEmpty()) {
                        val itemNames = cartItems.joinToString(separator = "\n") { it.name!! }
                        val itemPrices =
                            cartItems.joinToString(separator = "\n") { it.price.toString() }
                        val items = itemNames.split("\n")
                            .zip(itemPrices.split("\n"))
                            .joinToString(separator = "\n") { (name, price) -> "$name ($price)" }
                        val message = "Added to cart:\n$items"
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }

            }

            binding.cart!!.setOnClickListener {
                if (countNum == 0) {
                    Toast.makeText(this, "Cart is Empty", Toast.LENGTH_SHORT).show()
                } else {

                    val intent = Intent(this, CartActivity::class.java)
                    intent.putExtra("count", binding.cartValue!!.text.toString())
                    intent.putExtra("price", price)
                    intent.putExtra("name", bikename)
                    intent.putExtra("image", img)
                    startActivity(intent)
                }
            }

        }
    }

    private fun updateCartItemsQuantity(itemId: String, quantity: Int) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val cartRef = FirebaseDatabase.getInstance().reference.child("cartitems").child(uid)
        cartRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (cartSnap in snapshot.children) {
                    val id = cartSnap.child("id").getValue(String::class.java)
                    if (itemId == id) {
                        val key = cartSnap.key
                        cartRef.child(key.toString()).child("quantity").setValue(quantity)
                        orders.clear()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
               //error
            }
        })
    }

    companion object {
        val EXTRA_BIKE = Bike::class.java
    }

    private fun onCartItemAdded() {
        val cartItemsCount = cartViewModel.getCartItemsCount()
        val cartTv = binding.cartValue
        //cartTv!!.text = cartItemsCount.toString()
        // Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show()


    }

}