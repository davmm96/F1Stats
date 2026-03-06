package com.david.f1stats.ui.ranking.drivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentRankingDriversBinding
import com.david.f1stats.ui.SharedViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RankingDriversFragment : Fragment(), RankingDriversAdapter.RankingItemListener {

    private val rankingDriverViewModel: RankingDriversViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by activityViewModel()
    private val adapter: RankingDriversAdapter by lazy { RankingDriversAdapter(this) }

    private var _binding: FragmentRankingDriversBinding? = null
    private val binding get() = _binding!!

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
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.selectedSeason.collect {
                rankingDriverViewModel.fetchRankingDrivers()
            }
        }
    }

    private fun initRecyclerView() {
        binding.baseRankingLayout.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.baseRankingLayout.rvRanking.adapter = adapter
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            rankingDriverViewModel.rankingDriverList.collect { rankingDrivers ->
                adapter.setItems(ArrayList(rankingDrivers))
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            rankingDriverViewModel.isLoading.collect { isLoading ->
                binding.baseRankingLayout.progressBar.isVisible = isLoading
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            rankingDriverViewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    rankingDriverViewModel.clearErrorMessage()
                }
            }
        }
    }

    override fun onClickedDriver(driverId: Int) {
        findNavController().navigate(
            R.id.action_navigation_rankingDrivers_to_driverDetailFragment,
            bundleOf("id" to driverId)
        )
    }
}
