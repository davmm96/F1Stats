package com.david.f1stats.ui.ranking

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.david.f1stats.ui.ranking.drivers.RankingDriversFragment
import com.david.f1stats.ui.ranking.raceResult.RankingRacesFragment
import com.david.f1stats.ui.ranking.teams.RankingTeamsFragment

class RankingTabsAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {

    enum class Tab {
        DRIVERS, TEAMS, RACE_RESULTS
    }

    override fun getItemCount(): Int = Tab.values().size

    override fun createFragment(position: Int): Fragment {
        return when (Tab.values()[position]){
            Tab.DRIVERS -> RankingDriversFragment()
            Tab.TEAMS -> RankingTeamsFragment()
            Tab.RACE_RESULTS -> RankingRacesFragment()
        }
    }
}
