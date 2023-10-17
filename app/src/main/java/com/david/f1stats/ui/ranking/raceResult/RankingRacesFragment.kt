package com.david.f1stats.ui.ranking.raceResult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private val adapter: RankingRacesAdapter by lazy { RankingRacesAdapter(this, this, emptyList()) }
    private val racesViewModel: RankingRacesViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRankingRacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSelectedSeason()
        initRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeSelectedSeason() {
        sharedViewModel.selectedSeason.observe(viewLifecycleOwner) {
            racesViewModel.getRacesCompleted()
        }
    }

    private fun initRecyclerView() {
        binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRanking.adapter = adapter
    }

    private fun initObservers() {
        racesViewModel.racesCompletedList.observe(viewLifecycleOwner) { racesCompleted ->
            racesCompleted?.let {
                adapter.setItems(ArrayList(it))
            }
        }

        racesViewModel.favoriteRacesIds.observe(viewLifecycleOwner) { favoriteRaceIds ->
            favoriteRaceIds?.let {
               adapter.updateFavoriteRaces(ArrayList(it))
            }
        }

        racesViewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            binding.progressBar.isVisible = isLoading
        }
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
            Toast.makeText(context, getString(R.string.favorite_removed), Toast.LENGTH_SHORT).show()
        } else {
            racesViewModel.addRaceToFavorites(race)
            Toast.makeText(context, getString(R.string.favorite_added), Toast.LENGTH_SHORT).show()
        }
    }
}