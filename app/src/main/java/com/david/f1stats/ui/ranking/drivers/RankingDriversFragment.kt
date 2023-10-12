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
    private lateinit var adapter: RankingDriversAdapter
    private val rankingDriverViewModel: RankingDriversViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRankingDriversBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rankingDriverViewModel.onCreate()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.selectedSeason.observe(viewLifecycleOwner) {
            rankingDriverViewModel.onCreate()
        }

        setupRecyclerView()

        rankingDriverViewModel.rankingDriverList.observe(viewLifecycleOwner) {
            it?.let { it1 -> ArrayList(it1) }?.let { it2 -> adapter.setItems(it2) }
        }

        rankingDriverViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }
    }

    private fun setupRecyclerView() {
        adapter = RankingDriversAdapter(this)
        binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRanking.adapter = adapter
    }

    override fun onClickedDriver(driverId: Int) {
        findNavController().navigate(
            R.id.action_navigation_rankingDrivers_to_driverDetailFragment,
            bundleOf("id" to driverId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}