package com.david.f1stats.ui.ranking.races

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.david.f1stats.R

class RankingRacesFragment : Fragment() {

    companion object {
        fun newInstance() = RankingRacesFragment()
    }

    private lateinit var viewModel: RankingRacesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ranking_races, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RankingRacesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}