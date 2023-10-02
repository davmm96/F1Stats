package com.david.f1stats.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentRankingBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingFragment : Fragment(){

    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterTabs: RankingTabsAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

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

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        adapterTabs = RankingTabsAdapter(this)
        viewPager.adapter = adapterTabs

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.ranking_drivers)
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_driver)
                }
                1 -> {
                    tab.text = getString(R.string.ranking_teams)
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_team)
                }
                2 -> {
                    tab.text = getString(R.string.ranking_races)
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_ranking)
                }
                else -> throw IllegalArgumentException("Invalid position")
            }
        }.attach()
    }
}
