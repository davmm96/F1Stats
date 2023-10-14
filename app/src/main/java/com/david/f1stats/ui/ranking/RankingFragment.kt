package com.david.f1stats.ui.ranking

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentRankingBinding
import com.david.f1stats.ui.SharedViewModel
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
    private val sharedViewModel: SharedViewModel by activityViewModels()

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_dashboard, sharedViewModel.selectedSeason.value.toString())
        return binding.root
    }

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.selectedSeason.observe(viewLifecycleOwner) {
            (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_dashboard, sharedViewModel.selectedSeason.value.toString())
        }

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        adapterTabs = RankingTabsAdapter(this)
        viewPager.adapter = adapterTabs

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.ranking_drivers)
                }
                1 -> {
                    tab.text = getString(R.string.ranking_teams)
                }
                2 -> {
                    tab.text = getString(R.string.ranking_races)
                }
                else -> throw IllegalArgumentException("Invalid position")
            }
        }.attach()
    }
}
