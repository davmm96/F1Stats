package com.david.f1stats.ui.ranking

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.david.f1stats.ui.ranking.drivers.RankingDriversFragment
import com.david.f1stats.ui.ranking.races.RankingRacesFragment
import com.david.f1stats.ui.ranking.teams.RankingTeamsFragment
import com.david.f1stats.utils.Constants

class RankingTabsAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = Constants.NUM_TABS_RANKING

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RankingDriversFragment()
            1 -> RankingTeamsFragment()
            2 -> RankingRacesFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}