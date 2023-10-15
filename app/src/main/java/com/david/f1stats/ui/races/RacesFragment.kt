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
    private lateinit var adapter: RacesAdapter
    private val racesViewModel: RacesViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRacesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_home, sharedViewModel.selectedSeason.value.toString())
        racesViewModel.onCreate()

        return root
    }

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.selectedSeason.observe(viewLifecycleOwner) {
            (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_home, sharedViewModel.selectedSeason.value.toString())
            racesViewModel.onCreate()
        }

        setupRecyclerView()

        racesViewModel.raceList.observe(viewLifecycleOwner) { races ->
            races?.let {
                if (it.isEmpty()) {
                    binding.rvRaces.isVisible = false
                    binding.calendarTitle.isVisible = false
                } else {
                    binding.rvRaces.isVisible = true
                    binding.calendarTitle.isVisible = true
                    adapter.setItems(ArrayList(it))
                }
            }
        }

        racesViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }

        racesViewModel.isSeasonCompleted.observe(viewLifecycleOwner) { isCompleted ->
            binding.tvNoRaces.isVisible = isCompleted
            binding.tvNoRacesSubtitle.isVisible = isCompleted
            binding.ivNoRaces.isVisible = isCompleted
        }
    }

    private fun setupRecyclerView() {
        adapter = RacesAdapter(this)
        binding.rvRaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRaces.adapter = adapter
    }


    override fun onClickedRace(idCompetition: Int, country: String, idRace: Int) {
        findNavController().navigate(
            R.id.action_navigation_races_to_raceDetailFragment,
            bundleOf("id" to idCompetition, "country" to country)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
