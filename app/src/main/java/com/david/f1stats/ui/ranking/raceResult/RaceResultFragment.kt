package com.david.f1stats.ui.ranking.raceResult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.databinding.FragmentRaceResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RaceResultFragment : Fragment() {

    private var _binding: FragmentRaceResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RaceResultAdapter
    private val raceResultViewModel: RaceResultViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { raceResultViewModel.start(it) }

        setupRecyclerView()

        raceResultViewModel.raceResult.observe(viewLifecycleOwner) {
            it?.let { it1 -> ArrayList(it1) }?.let { it2 -> adapter.setItems(it2) }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRaceResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = RaceResultAdapter()
        binding.rvResults.layoutManager = LinearLayoutManager(requireContext())
        binding.rvResults.adapter = adapter
    }
}