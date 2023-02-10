package com.aisc.ngalo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aisc.ngalo.admin.Advert
import com.aisc.ngalo.admin.CreateAdvert
import com.aisc.ngalo.databinding.ActivityHomeBinding
import com.aisc.ngalo.models.Bike
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUserUID = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef = FirebaseDatabase
            .getInstance()
            .reference
            .child("users")
            .child(currentUserUID)
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

        val addRef = FirebaseDatabase.getInstance().reference.child("adverts")
        addRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ads = snapshot.child("imageUrl").getValue(String::class.java)
                Glide.with(binding.root)
                    .load(ads)
                    .into(binding.ngaloAd)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.settingsHome.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.myaccountHome.setOnClickListener {
            startActivity(Intent(this, MyAccount::class.java))
        }
    }
}