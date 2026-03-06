package com.david.f1stats.ui.ranking.teams

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
import com.david.f1stats.databinding.FragmentRankingTeamsBinding
import com.david.f1stats.ui.SharedViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RankingTeamsFragment : Fragment(), RankingTeamsAdapter.RankingItemListener {

    private val rankingTeamViewModel: RankingTeamsViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by activityViewModel()
    private val adapter: RankingTeamsAdapter by lazy { RankingTeamsAdapter(this) }

    private var _binding: FragmentRankingTeamsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingTeamsBinding.inflate(inflater, container, false)
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
                rankingTeamViewModel.fetchRankingTeams()
            }
        }
    }

    private fun initRecyclerView() {
        binding.baseRankingLayout.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.baseRankingLayout.rvRanking.adapter = adapter
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            rankingTeamViewModel.rankingTeamList.collect { rankingTeams ->
                adapter.setItems(ArrayList(rankingTeams))
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            rankingTeamViewModel.isLoading.collect { isLoading ->
                binding.baseRankingLayout.progressBar.isVisible = isLoading
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            rankingTeamViewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    rankingTeamViewModel.clearErrorMessage()
                }
            }
        }
    }

    override fun onClickedRankingTeam(rankingTeamId: Int) {
        findNavController().navigate(
            R.id.action_navigation_rankingTeams_to_teamDetailFragment,
            bundleOf("id" to rankingTeamId)
        )
    }
}
