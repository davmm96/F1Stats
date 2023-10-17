package com.david.f1stats.ui.ranking.drivers

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
import com.david.f1stats.databinding.FragmentRankingDriversBinding
import com.david.f1stats.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingDriversFragment : Fragment(), RankingDriversAdapter.RankingItemListener{

    private var _binding: FragmentRankingDriversBinding? = null
    private val binding get() = _binding!!
    private val adapter: RankingDriversAdapter by lazy { RankingDriversAdapter(this) }
    private val rankingDriverViewModel: RankingDriversViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingDriversBinding.inflate(inflater, container, false)
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
            rankingDriverViewModel.fetchRankingDrivers()
        }
    }

    private fun initRecyclerView() {
        binding.baseRankingLayout.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.baseRankingLayout.rvRanking.adapter = adapter
    }

    private fun initObservers(){
        rankingDriverViewModel.rankingDriverList.observe(viewLifecycleOwner) {rankingDrivers ->
            rankingDrivers?.let {
                adapter.setItems(ArrayList(it))
            }
        }

        rankingDriverViewModel.isLoading.observe(viewLifecycleOwner){
            binding.baseRankingLayout.progressBar.isVisible = it
        }
    }

    override fun onClickedDriver(driverId: Int) {
        findNavController().navigate(
            R.id.action_navigation_rankingDrivers_to_driverDetailFragment,
            bundleOf("id" to driverId)
        )
    }
}
