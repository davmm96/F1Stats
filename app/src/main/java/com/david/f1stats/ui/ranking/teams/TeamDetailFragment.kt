package com.david.f1stats.ui.ranking.teams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.david.f1stats.databinding.FragmentTeamDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TeamDetailFragment : Fragment() {

    @Inject
    lateinit var picasso: Picasso

    private var _binding: FragmentTeamDetailBinding? = null
    private val binding get() = _binding!!
    private val teamDetailViewModel: TeamDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { teamDetailViewModel.start(it) }

        teamDetailViewModel.teamInfo.observe(viewLifecycleOwner) {
            binding.tvTeamName.text = it.name
            binding.tvTeamCountry.text = it.location
            binding.tvWC.text = it.worldChampionships
            binding.tvFirstSeason.text = it.firstSeason
            binding.tvWins.text = it.wins
            binding.tvPolePositions.text = it.polePositions
            binding.tvFastestLaps.text = it.fastestLaps
            picasso
                .load(it.image)
                .into(binding.ivTeamImage)
        }
    }

}