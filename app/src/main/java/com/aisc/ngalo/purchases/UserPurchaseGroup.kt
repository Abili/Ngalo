package com.aisc.ngalo.purchases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.databinding.ActivityUserGroupBinding

class UserPurchaseGroup : Fragment() {
    private lateinit var binding: ActivityUserGroupBinding
    private lateinit var adapter: UserGroupPurchasesAdapter
    private lateinit var purchasesViewModel: PurchasesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        purchasesViewModel = ViewModelProvider(this)[PurchasesViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityUserGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserGroupPurchasesAdapter()
        binding.usergrouprecycler.adapter = adapter
        binding.usergrouprecycler.layoutManager = LinearLayoutManager(requireContext())
        purchasesViewModel.userGroups()
        purchasesViewModel.users.observe(viewLifecycleOwner) {
            adapter.add(it)
        }
    }

    class User(
        val image: String? = "",
        val name: String? = "",
        val location: String? = "",
        val pickuplocation: String? = ""
    )
}
