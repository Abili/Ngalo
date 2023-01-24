package com.aisc.ngalo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aisc.ngalo.admin.UploadImages
import com.aisc.ngalo.databinding.ActivityBikesOptionsBinding

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


        binding.fabAddBike.setOnClickListener {
            startActivity(Intent(this@BikesOptions, UploadImages::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        // Customize the SearchView
        searchView.queryHint = "Search..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform the search here
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
                        this@BikesOptions,
                        SettingsActivity::class.java
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

class PagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BikeForPurchaseFragment()
            1 -> BikesForHireFragment()
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Bike for Purchase"
            1 -> "Bikes for Hire"
            else -> ""
        }
    }

}