package com.aisc.ngalo.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.OrdersActivityBinding

class UserRepairRequestActivity : Fragment(), OrdersAdapter.OrderClickListener {
    private var _binding: OrdersActivityBinding? = null
    private val binding get() = _binding!!
    private val adapter = OrdersAdapter()
    private var ordersViewModel: OrdersViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OrdersActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ordersViewModel = ViewModelProvider(this)[OrdersViewModel::class.java]

        ordersViewModel!!.loadRepaireRequests()
        ordersViewModel!!.completedRequests.observe(viewLifecycleOwner) { requests ->
            if (requests.isNotEmpty()) {
                adapter.add(requests)
                adapter.notifyDataSetChanged()
            } else {
                binding.nodataTv.text = getString(R.string.no_orders_available)
                binding.ordersRecycler.visibility = View.GONE
                binding.nodataLayout.visibility = View.VISIBLE

            }
        }

        binding.ordersRecycler.adapter = adapter
        binding.ordersRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter.listener = this

//        adapter.setOnCartUpdatedListener(object : OrdersAdapter.OrderClickListener {
//            override fun onOrderCompleted(order: Order) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOrderCompleted(order: Order) {
        adapter.orders.remove(order)
        adapter.notifyDataSetChanged()
        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show()
    }
}
