package com.david.f1stats.ui.ranking.teams.teamDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.david.f1stats.databinding.FragmentTeamDetailBinding
import com.david.f1stats.utils.Constants
import com.david.f1stats.utils.DialogHelper
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TeamDetailFragment : Fragment() {

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var dialogHelper: DialogHelper

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
        arguments?.getInt("id")?.let { teamDetailViewModel.fetchTeamDetail(it) }

        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers(){
        teamDetailViewModel.teamInfo.observe(viewLifecycleOwner) { team ->
            binding.apply {
                tvTeamName.text = team.name
                tvTeamCountry.text = team.location
                tvWC.text = team.worldChampionships
                tvFirstSeason.text = team.firstSeason
                tvWins.text = team.wins
                tvPolePositions.text = team.polePositions
                tvFastestLaps.text = team.fastestLaps

                loadImage(ivTeamImage, team.image)
            }
        }
    }

    private fun loadImage(view: ImageView, imageUrl: String?) {
        picasso.load(imageUrl).into(view)
        setupImageClickListener(view, imageUrl)
    }

    private fun setupImageClickListener(view: View, imageUrl: String?) {
        view.setOnClickListener {
            if (imageUrl != null && imageUrl != Constants.IMAGE_NOT_FOUND) {
                dialogHelper.showImageDialog(requireActivity(), picasso, imageUrl)
            }
        }
    }
}
