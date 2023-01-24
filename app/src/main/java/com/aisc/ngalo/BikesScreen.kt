package com.aisc.ngalo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.admin.UploadImages
import com.aisc.ngalo.databinding.ActivityBikesScreenBinding
import com.aisc.ngalo.models.Bike
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BikesScreen() :
    AppCompatActivity() {
    private lateinit var binding: ActivityBikesScreenBinding
    private lateinit var bikeViewModel: BikeViewModel
    private lateinit var bikesAdapter: BikesAdapter
    private lateinit var firebaseRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val repository = BikeRepository.getInstance(this)

        firebaseRef = FirebaseDatabase.getInstance().reference.child("bikes")
        //val bikeRef = firebaseRef.reference.child("bikes")

        bikeViewModel =
            ViewModelProvider(
                this,
                BikeViewModel.BikeViewModelFactory(repository)
            )[BikeViewModel::class.java]

        binding = ActivityBikesScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.Theme_AppCompat)
        lifecycleScope.launch(Dispatchers.IO) {
            bikeViewModel.fetchData()
            withContext(Dispatchers.Main) {
//                binding.bikeRecyclerView.adapter = bikesAdapter

//                bikeViewModel.getAllBikes().observe(this@BikesScreen) {
//                    Toast.makeText(this@BikesScreen, it.toString(), Toast.LENGTH_SHORT).show()
//                    bikesAdapter.currentList
//                    binding.bikeRecyclerView.layoutManager = LinearLayoutManager(this@BikesScreen)
//                }
//                bikeViewModel.getAllBikes().observe(this@BikesScreen) {
//                    //update UI
//                    binding.bikeRecyclerView.adapter = bikesAdapter
//                }
                bikesAdapter = BikesAdapter()
                binding.bikeRecyclerView.adapter = bikesAdapter
                binding.bikeRecyclerView.layoutManager = LinearLayoutManager(
                    this@BikesScreen,
                    LinearLayoutManager.VERTICAL, false
                )

                firebaseRef
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            // Clear the adapter and add the playlists from the snapshot
                            // Clear the adapters
                            bikesAdapter.clear()
                            val bike = snapshot.children.mapNotNull { child ->
                                child.getValue(Bike::class.java)
                            }
                            bike.forEach { bikesAdapter.add(it) }

                            // Notify the adapters that the data has changed

                            bikesAdapter.notifyDataSetChanged()

                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle error
                        }
                    })


            }

        }
        binding.fbAddBike.setOnClickListener {
            startActivity(Intent(this@BikesScreen, UploadImages::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bikeViewModel.getAllBikes().removeObservers(this)
    }
}
