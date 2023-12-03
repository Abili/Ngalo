package com.aisc.ngalo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.aisc.ngalo.cart.CartActivity
import com.aisc.ngalo.cart.CartRepository
import com.aisc.ngalo.cart.CartViewModel
import com.aisc.ngalo.cart.CartViewModelFactory
import com.aisc.ngalo.databinding.ActivityHomeBinding
import com.aisc.ngalo.rides.RidesActivity
import com.aisc.ngalo.usersorders.History
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private val locationPermissionCode = 42

    // Access the CartRepository using the application instance
    private val cartRepository: CartRepository
        get() = (application as NgaloApplication).cartRepository

    // Access the CartViewModel using the CartRepository
    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(cartRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = PreferenceManager.getDefaultSharedPreferences(this).getString("theme", "system")
            ?: "system"
        ThemeHelper.applyTheme(theme)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        // Check if location permission is granted

        if (currentUser != null) {
            val currentUserUID = currentUser.uid
            // do something with currentUserUID
            val userRef =
                FirebaseDatabase.getInstance().reference.child("users").child(currentUserUID)
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        startActivity(Intent(this@HomeActivity, UserProfile::class.java))
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Snackbar.make(binding.root, error.message, Snackbar.LENGTH_SHORT).show()
                }

            })

        } else {
            // handle null case
            startActivity(Intent(this@HomeActivity, SignUp::class.java))
            finish()
        }

        if (isLocationPermissionGranted()) {
            // Location permission is granted, proceed with your app logic
            appLogic()
            // ...
        } else {
            // Location permission is not granted, request it
            requestLocationPermission()
        }


    }

    private fun appLogic() {
        // cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        cartViewModel.getCartItemsCount()

        cartViewModel.observeCartItemsCount().observe(this) { count ->
            // Update your UI with the count of items in the cart
            binding.cartHomeText.text = count.toString()
        }

        binding.buy.setOnClickListener {
            val intent = Intent(this, BikesOptions::class.java)
            intent.putExtra("position", 0)
            startActivity(Intent(intent))
        }
        binding.hire.setOnClickListener {
            val intent = Intent(this, BikesOptions::class.java)
            intent.putExtra("position", 1)
            startActivity(Intent(intent))
        }
        binding.bikeparts.setOnClickListener {
            val intent = Intent(this, BikesOptions::class.java)
            intent.putExtra("position", 2)
            startActivity(Intent(intent))
        }
        binding.repairs.setOnClickListener {
            val intent = Intent(this, BikeRepair::class.java)
            intent.putExtra("position", 2)
            startActivity(Intent(intent))
        }
        binding.rides.setOnClickListener {
            val intent = Intent(this, RidesForBooking::class.java)
            startActivity(Intent(intent))
        }

        val addRef = FirebaseDatabase.getInstance().reference.child("adverts")
        addRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ads = snapshot.child("imageUrl").getValue(String::class.java)
                Glide.with(binding.root).load(ads).into(binding.ngaloAd)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HomeActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })

        binding.settingsHome.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.myaccountHome.setOnClickListener {
            startActivity(Intent(this, MyAccount::class.java))
        }
        binding.historyHome.setOnClickListener {
            startActivity(Intent(this, History::class.java))
        }
        binding.cartHome.setOnClickListener {
            if (binding.cartHomeText.text == "0") {
                Toast.makeText(this, "Cart is Empty", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, CartActivity::class.java))
            }
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, proceed with your app logic
                appLogic()
                // ...
            } else {
                // Location permission denied, handle accordingly
                finish()
                // ...
            }
        }
    }

}