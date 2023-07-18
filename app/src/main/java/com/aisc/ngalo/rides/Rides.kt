package com.aisc.ngalo.rides

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.databinding.ActivityRides2Binding

class Rides : AppCompatActivity() {
    var ridesViewModel: RidesViewModel? = null
    var binding: ActivityRides2Binding? = null
    private val allRidesAdapter = AllRidesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRides2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding!!.root)


        binding!!.ridesrecycler.adapter = allRidesAdapter
        binding!!.ridesrecycler.layoutManager = LinearLayoutManager(this)

        ridesViewModel = ViewModelProvider(this)[RidesViewModel::class.java]
        ridesViewModel!!.loadAllRides()
        ridesViewModel!!.rides.observe(this) {
            allRidesAdapter.add(it)

        }
    }
}