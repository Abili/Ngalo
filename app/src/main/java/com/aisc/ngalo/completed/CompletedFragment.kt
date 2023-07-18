package com.aisc.ngalo.completed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aisc.ngalo.databinding.ActivityCompletedBinding


class CompletedFragment : Fragment() {
    private lateinit var completedViewModel: CompletedViewModel
    private lateinit var binding: ActivityCompletedBinding
    private val adapter = CompletedAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityCompletedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        completedViewModel = ViewModelProvider(this)[CompletedViewModel::class.java]

        binding.completedRecycler.adapter = adapter
        binding.completedRecycler.layoutManager = LinearLayoutManager(requireContext())

        completedViewModel.loadCompletedRequests()
        completedViewModel.completedRequests.observe(viewLifecycleOwner) { completedRequests ->
            adapter.add(completedRequests)
        }
    }
}
