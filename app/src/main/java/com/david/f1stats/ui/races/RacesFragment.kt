package com.david.f1stats.ui.races

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentRacesBinding
import com.david.f1stats.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RacesFragment : Fragment(), RacesAdapter.RaceItemListener {

    private var _binding: FragmentRacesBinding? = null
    private val binding get() = _binding!!
    private val adapter: RacesAdapter by lazy { RacesAdapter(this) }
    private val racesViewModel: RacesViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRacesBinding.inflate(inflater, container, false)
        updateActionBarTitle()

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

    @SuppressLint("StringFormatInvalid")
    private fun updateActionBarTitle() {
        val title = getString(R.string.title_home, sharedViewModel.selectedSeason.value.toString())
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }

    private fun observeSelectedSeason() {
        sharedViewModel.selectedSeason.observe(viewLifecycleOwner) {
            updateActionBarTitle()
            racesViewModel.fetchRaces()
        }
    }

    private fun initObservers(){
        racesViewModel.raceList.observe(viewLifecycleOwner) { races ->
            val hasRaces = races?.isNotEmpty() == true
            binding.apply {
                rvRaces.isVisible = hasRaces
                calendarTitle.isVisible = hasRaces
                if (hasRaces) races?.let { adapter.setItems(ArrayList(it)) }
            }
        }

        racesViewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        racesViewModel.isSeasonCompleted.observe(viewLifecycleOwner) { isCompleted ->
            binding.apply {
                tvNoRaces.isVisible = isCompleted
                tvNoRacesSubtitle.isVisible = isCompleted
                ivNoRaces.isVisible = isCompleted
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvRaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRaces.adapter = adapter
    }

    override fun onClickedRace(idCompetition: Int, country: String, idRace: Int) {
        findNavController().navigate(
            R.id.action_navigation_races_to_raceDetailFragment,
            bundleOf("id" to idCompetition, "country" to country)
        )
    }
}
