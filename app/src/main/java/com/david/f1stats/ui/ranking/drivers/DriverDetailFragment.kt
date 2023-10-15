package com.david.f1stats.ui.ranking.drivers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.david.f1stats.databinding.FragmentDriverDetailBinding
import com.david.f1stats.utils.Constants
import com.david.f1stats.utils.DialogHelper
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DriverDetailFragment : Fragment() {

    @Inject
    lateinit var picasso: Picasso

    private var _binding: FragmentDriverDetailBinding? = null
    private val binding get() = _binding!!
    private val driverDetailViewModel: DriverDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { driverDetailViewModel.start(it) }

        driverDetailViewModel.driverInfo.observe(viewLifecycleOwner) {
            binding.tvDriverName.text = it.name
            binding.tvDriverNumber.text = it.number
            binding.tvDriverCountry.text = it.country
            binding.tvWC.text = it.worldChampionships
            binding.tvPodiums.text = it.podiums
            binding.tvRaces.text = it.gpEntered
            binding.tvWins.text = it.wins
            binding.tvPoints.text = it.points
            picasso
                .load(it.image)
                .into(binding.ivDriverImage)

            picasso
                .load(it.teamImage)
                .into(binding.ivActualTeam)
        }

        binding.ivDriverImage.setOnClickListener {
            val imageUrl = driverDetailViewModel.driverInfo.value?.image
            if (imageUrl != null && imageUrl != Constants.IMAGE_NOT_FOUND) {
                DialogHelper.showImageDialog(requireActivity(), picasso, imageUrl)
            }
        }
    }
}