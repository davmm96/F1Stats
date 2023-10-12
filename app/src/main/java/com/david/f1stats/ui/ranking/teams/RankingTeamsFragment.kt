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
    private lateinit var adapter: RankingTeamsAdapter
    private val rankingTeamViewModel: RankingTeamsViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRankingTeamsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rankingTeamViewModel.onCreate()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.selectedSeason.observe(viewLifecycleOwner) {
            rankingTeamViewModel.onCreate()
        }

        setupRecyclerView()

        rankingTeamViewModel.rankingTeamList.observe(viewLifecycleOwner) {
            it?.let { it1 -> ArrayList(it1) }?.let { it2 -> adapter.setItems(it2) }
        }

        rankingTeamViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }
    }

    private fun setupRecyclerView() {
        adapter = RankingTeamsAdapter(this)
        binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRanking.adapter = adapter
    }

    override fun onClickedRankingTeam(rankingTeamId: Int) {
        findNavController().navigate(
            R.id.action_navigation_rankingTeams_to_teamDetailFragment,
            bundleOf("id" to rankingTeamId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}