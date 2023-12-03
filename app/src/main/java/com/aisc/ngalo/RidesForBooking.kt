package com.aisc.ngalo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.databinding.ActivityRidesForBookingBinding
import com.aisc.ngalo.models.BookRide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RidesForBooking : AppCompatActivity() {
    lateinit var adapter: RideAdapter
    lateinit var binding: ActivityRidesForBookingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRidesForBookingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter = RideAdapter()
        binding.bookridesrecycler.adapter = adapter
        binding.bookridesrecycler.layoutManager = LinearLayoutManager(this)


        val rideBookingRef = FirebaseDatabase.getInstance().reference.child("bookride")
        rideBookingRef.addValueEventListener(object : ValueEventListener {
            val rideList = mutableListOf<BookRide>()
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ridesnap in snapshot.children) {
                    val rideId = ridesnap.child("rideId").getValue(String::class.java)
                    val date = ridesnap.child("date").getValue(String::class.java)
                    val time = ridesnap.child("time").getValue(String::class.java)
                    val distance = ridesnap.child("distance").getValue(String::class.java)
                    val name = ridesnap.child("name").getValue(String::class.java)
                    val rides = BookRide(rideId, name, distance, date, time)

                    adapter.add(rides)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}