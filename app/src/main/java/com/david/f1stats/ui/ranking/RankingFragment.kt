package com.david.f1stats.ui.ranking

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentRankingBinding
import com.david.f1stats.ui.SharedViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingFragment : Fragment(){

    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSelectedSeason()
        initViewPager()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("StringFormatInvalid")
    private fun observeSelectedSeason() {
        sharedViewModel.selectedSeason.observe(viewLifecycleOwner) { season ->
            val title = getString(R.string.title_ranking, season)
            (requireActivity() as AppCompatActivity).supportActionBar?.title = title
        }
    }

    private fun initViewPager() {
        binding.apply {
            viewPager.adapter = RankingTabsAdapter(this@RankingFragment)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.ranking_drivers)
                    1 -> getString(R.string.ranking_teams)
                    2 -> getString(R.string.ranking_races)
                    else -> throw IllegalArgumentException("Invalid position")
                }
            }.attach()
        }
    }
}
