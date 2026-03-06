package com.david.f1stats.ui.races

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentRacesBinding
import com.david.f1stats.ui.SharedViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RacesFragment : Fragment(), RacesAdapter.RaceItemListener {

    private val racesViewModel: RacesViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by activityViewModel()
    private val adapter: RacesAdapter by lazy { RacesAdapter(this) }

    private var _binding: FragmentRacesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRacesBinding.inflate(inflater, container, false)
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
    private fun observeSelectedSeason() {
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.selectedSeason.collect {
                val title = getString(R.string.title_calendar, sharedViewModel.selectedSeason.value)
                (requireActivity() as AppCompatActivity).supportActionBar?.title = title
                racesViewModel.fetchRaces()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvRaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRaces.adapter = adapter
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            racesViewModel.raceList.collect { races ->
                val hasRaces = races?.isNotEmpty() == true
                binding.apply {
                    rvRaces.isVisible = hasRaces
                    calendarTitle.isVisible = hasRaces
                    if (hasRaces) races?.also { adapter.setItems(ArrayList(it)) }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            racesViewModel.isLoading.collect { isLoading ->
                binding.progressBar.isVisible = isLoading
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            racesViewModel.isSeasonCompleted.collect { isCompleted ->
                binding.apply {
                    tvNoRaces.isVisible = isCompleted
                    tvNoRacesSubtitle.isVisible = isCompleted
                    ivNoRaces.isVisible = isCompleted
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            racesViewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    racesViewModel.clearErrorMessage()
                }
            }
        }
    }

    override fun onClickedRace(idCompetition: Int, country: String, idRace: Int) {
        findNavController().navigate(
            R.id.action_navigation_races_to_raceDetailFragment,
            bundleOf("id" to idCompetition, "country" to country)
        )
    }
}
