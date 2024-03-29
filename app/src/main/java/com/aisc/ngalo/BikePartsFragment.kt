package com.aisc.ngalo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.databinding.FragmentBikePartsBinding
import com.aisc.ngalo.models.Bike
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BikePartsFragment : Fragment() {
    private lateinit var bikeViewModel: BikeViewModel
    private lateinit var bikesAdapter: BikesPartsAdapter
    private lateinit var firebaseRef: DatabaseReference
    private lateinit var binding: FragmentBikePartsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBikePartsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up UI components here, such as setting up RecyclerView, etc.

        FirebaseApp.initializeApp(requireContext())
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext())
        val repository = BikeRepository.getInstance(requireContext())

        firebaseRef = FirebaseDatabase.getInstance().reference.child("bikepart")
        //val bikeRef = firebaseRef.reference.child("bikes")

        bikeViewModel =
            ViewModelProvider(
                this,
                BikeViewModel.BikeViewModelFactory(repository)
            )[BikeViewModel::class.java]

        MobileAds.initialize(requireContext()) { initiallized ->
            if (initiallized.equals(true)) {
                val adRequest = AdRequest.Builder().build()
                binding.adView!!.loadAd(adRequest)
            }
        }

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
                bikesAdapter = BikesPartsAdapter(null)
                binding.bikepartsRecyclerView.adapter = bikesAdapter
                binding.bikepartsRecyclerView.layoutManager = LinearLayoutManager(
                    requireContext(),
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
//        binding.fbAddBike.setOnClickListener {
//            startActivity(Intent(this@BikesScreen, UploadImages::class.java))
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bikeViewModel.getAllBikes().removeObservers(this)
    }
}