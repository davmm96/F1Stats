package com.david.f1stats.ui.ranking.races

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.databinding.FragmentRankingRacesBinding
import com.david.f1stats.ui.races.RacesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingRacesFragment : Fragment(), RacesAdapter.RaceItemListener {

    private var _binding: FragmentRankingRacesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RacesAdapter
    private val racesViewModel: RankingRacesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRankingRacesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        racesViewModel.onCreate()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        racesViewModel.racesCompletedList.observe(viewLifecycleOwner) {
            it?.let { it1 -> ArrayList(it1) }?.let { it2 -> adapter.setItems(it2) }
        }

        racesViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }
    }

    private fun setupRecyclerView() {
        adapter = RacesAdapter(this)
        binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRanking.adapter = adapter
    }


    override fun onClickedRace(raceId: Int, country: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}