package com.david.f1stats.ui.ranking.raceResult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentRankingRacesBinding
import com.david.f1stats.domain.model.Race
import com.david.f1stats.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingRacesFragment : Fragment(),
    RankingRacesAdapter.RankingRacesNavListener,
    RankingRacesAdapter.RankingRacesFavListener {

    private var _binding: FragmentRankingRacesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RankingRacesAdapter
    private val racesViewModel: RankingRacesViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

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

        sharedViewModel.selectedSeason.observe(viewLifecycleOwner) {
            racesViewModel.onCreate()
        }

        setupRecyclerView()

        racesViewModel.racesCompletedList.observe(viewLifecycleOwner) {
            it?.let { it1 -> ArrayList(it1) }?.let { it2 -> adapter.setItems(it2) }
        }

        racesViewModel.favoriteRacesIds.observe(viewLifecycleOwner) {
            it?.let { it1 -> ArrayList(it1) }?.let { it2 -> adapter.updateFavoriteRaces(it2) }
        }

        racesViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }
    }

    private fun setupRecyclerView() {
        adapter = RankingRacesAdapter(this, this, emptyList())
        binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRanking.adapter = adapter
    }


    override fun onNavClicked(idRace: Int, country: String) {
        findNavController().navigate(
            R.id.action_navigation_raceResults_to_raceResultFragment,
            bundleOf("id" to idRace, "country" to country)
        )
    }

    override fun onFavClicked(race: Race) {
        if (racesViewModel.favoriteRacesIds.value?.contains(race.idRace) == true) {
            racesViewModel.removeRaceFromFavorites(race.idRace)
        } else {
            racesViewModel.addRaceToFavorites(race)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}