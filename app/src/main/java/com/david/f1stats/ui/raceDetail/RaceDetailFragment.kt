package com.david.f1stats.ui.raceDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.david.f1stats.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RaceDetailFragment : Fragment() {

    val args:RaceDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.idRace
        val text = view.findViewById<TextView>(R.id.idRace)
        text.text = id
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_race_detail, container, false)
    }

}
