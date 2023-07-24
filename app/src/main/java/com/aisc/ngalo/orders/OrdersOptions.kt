package com.aisc.ngalo.orders

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.aisc.ngalo.BikesAdapter
import com.aisc.ngalo.R
import com.aisc.ngalo.cart.CartViewModel
import com.aisc.ngalo.completed.CompletedFragment
import com.aisc.ngalo.databinding.ActivityBikesOptionsBinding
import com.aisc.ngalo.purchases.PurchasesViewModel
import com.aisc.ngalo.purchases.UserPurchaseGroup

class OrdersOptions : AppCompatActivity() {

    private lateinit var binding: ActivityBikesOptionsBinding
    private var bikesAdapter: BikesAdapter? = null
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartTextView: TextView
    private lateinit var purchasViewModel: PurchasesViewModel
    private lateinit var ordersViewModel: OrdersViewModel
    var purchaseCount: String? = null
    val Count: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBikesOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = PagerAdapter(supportFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.tabs.setBackgroundColor(resources.getColor(R.color.ngalo_green))
        // binding.tabs.setTabTextColors(R.color.ngalo_green,R.color.ngalo_green)
        setSupportActionBar(binding.optionstoolbar)
        purchasViewModel = ViewModelProvider(this)[PurchasesViewModel::class.java]
        ordersViewModel = ViewModelProvider(this)[OrdersViewModel::class.java]

        ordersViewModel.orderCount()



    }

    class PagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> UserPurchaseGroup()
                1 -> UserRepairRequestActivity()
                2 -> CompletedFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> "Purchses"
                1 -> "Repairs"
                2 -> "Completed"
                else -> ""
            }
        }

    }
}