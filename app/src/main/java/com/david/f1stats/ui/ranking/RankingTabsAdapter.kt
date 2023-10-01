package com.david.f1stats.ui.ranking

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.david.f1stats.ui.circuits.CircuitsFragment
import com.david.f1stats.ui.races.RacesFragment

class RankingTabsAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3 // Number of tabs

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RacesFragment()
            1 -> CircuitsFragment()
            2 -> RacesFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}