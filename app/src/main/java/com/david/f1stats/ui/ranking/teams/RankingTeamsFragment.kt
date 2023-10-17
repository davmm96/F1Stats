package com.david.f1stats.ui.ranking.teams

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
import com.david.f1stats.databinding.FragmentRankingTeamsBinding
import com.david.f1stats.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingTeamsFragment : Fragment(), RankingTeamsAdapter.RankingItemListener{

    private var _binding: FragmentRankingTeamsBinding? = null
    private val binding get() = _binding!!
    private val adapter: RankingTeamsAdapter by lazy { RankingTeamsAdapter(this) }
    private val rankingTeamViewModel: RankingTeamsViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

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
        sharedViewModel.selectedSeason.observe(viewLifecycleOwner) {
            rankingTeamViewModel.fetchRankingTeams()
        }
    }

    private fun initRecyclerView() {
        binding.baseRankingLayout.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.baseRankingLayout.rvRanking.adapter = adapter
    }

    private fun initObservers(){
        rankingTeamViewModel.rankingTeamList.observe(viewLifecycleOwner) {rankingTeams ->
            rankingTeams?.let { adapter.setItems(ArrayList(it)) }
        }

        rankingTeamViewModel.isLoading.observe(viewLifecycleOwner){
            binding.baseRankingLayout.progressBar.isVisible = it
        }
    }

    override fun onClickedRankingTeam(rankingTeamId: Int) {
        findNavController().navigate(
            R.id.action_navigation_rankingTeams_to_teamDetailFragment,
            bundleOf("id" to rankingTeamId)
        )
    }
}
