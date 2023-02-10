package com.aisc.ngalo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aisc.ngalo.admin.UploadItems
import com.aisc.ngalo.databinding.ActivityBikesOptionsBinding
import com.aisc.ngalo.databinding.SearchviewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BikesOptions : AppCompatActivity() {

    private lateinit var binding: ActivityBikesOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBikesOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = PagerAdapter(supportFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.tabs.setBackgroundColor(resources.getColor(R.color.ngalo_green))
        // binding.tabs.setTabTextColors(R.color.ngalo_green,R.color.ngalo_green)
        setSupportActionBar(binding.optionstoolbar)

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

        binding.fabAddBike.setOnClickListener {
            startActivity(Intent(this@BikesOptions, UploadItems::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

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

//        val searchResults = ArrayList<String>()
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
//                                searchResults.clear()
//                                searchResults.add(result!!)

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
            R.id.action_settings -> {
                startActivity(
                    Intent(
                        this@BikesOptions, SettingsActivity::class.java
                    )
                )
                true
            }

//            R.id.action_search -> {
//                binding.searchOptions.visibility = View.VISIBLE
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}

class PagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

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