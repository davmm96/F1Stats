package com.david.f1stats.ui.ranking.teams.teamDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil3.ImageLoader
import coil3.load
import com.david.f1stats.databinding.FragmentTeamDetailBinding
import com.david.f1stats.utils.Constants
import com.david.f1stats.utils.DialogHelper
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeamDetailFragment : Fragment() {

    private val imageLoader: ImageLoader by inject()
    private val dialogHelper: DialogHelper by inject()
    private val teamDetailViewModel: TeamDetailViewModel by viewModel()

    private var _binding: FragmentTeamDetailBinding? = null
    private val binding get() = _binding!!

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

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            teamDetailViewModel.teamInfo.collect { team ->
                team?.let {
                    binding.apply {
                        tvTeamName.text = it.name
                        tvTeamCountry.text = it.location
                        tvWC.text = it.worldChampionships
                        tvFirstSeason.text = it.firstSeason
                        tvWins.text = it.wins
                        tvPolePositions.text = it.polePositions
                        tvFastestLaps.text = it.fastestLaps
                        loadImage(ivTeamImage, it.image)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            teamDetailViewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    teamDetailViewModel.clearErrorMessage()
                }
            }
        }
    }

    private fun loadImage(view: ImageView, imageUrl: String?) {
        view.load(imageUrl, imageLoader)
        setupImageClickListener(view, imageUrl)
    }

    private fun setupImageClickListener(view: View, imageUrl: String?) {
        view.setOnClickListener {
            if (imageUrl != null && imageUrl != Constants.IMAGE_NOT_FOUND) {
                dialogHelper.showImageDialog(requireActivity(), imageLoader, imageUrl)
            }
        }
    }
}
