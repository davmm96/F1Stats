package com.david.f1stats.ui.races

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentRacesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RacesFragment : Fragment(), RacesAdapter.RaceItemListener {

    private var _binding: FragmentRacesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RacesAdapter
    private val racesViewModel: RacesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRacesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        racesViewModel.onCreate()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        racesViewModel.raceList.observe(viewLifecycleOwner) {
            it?.let { it1 -> ArrayList(it1) }?.let { it2 -> adapter.setItems(it2) }
        }

        racesViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }

        racesViewModel.isSeasonCompleted.observe(viewLifecycleOwner) { isCompleted ->
            binding.seasonCompletedMessage.isVisible = isCompleted
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
