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

        driverDetailViewModel.driverInfo.observe(viewLifecycleOwner) { driver ->
            binding.tvDriverName.text = driver.name
            binding.tvDriverNumber.text = driver.number
            binding.tvDriverCountry.text = driver.country
            binding.tvWC.text = driver.worldChampionships
            binding.tvPodiums.text = driver.podiums
            binding.tvRaces.text = driver.gpEntered
            binding.tvWins.text = driver.wins
            binding.tvPoints.text = driver.points
            picasso.load(driver.image).into(binding.ivDriverImage)
            picasso.load(driver.teamImage).into(binding.ivActualTeam)

            setupImageClickListener(binding.ivDriverImage, driver.image)
            setupImageClickListener(binding.ivActualTeam, driver.teamImage)
        }
    }

    private fun setupImageClickListener(view: View, imageUrl: String?) {
        view.setOnClickListener {
            if (imageUrl != null && imageUrl != Constants.IMAGE_NOT_FOUND) {
                DialogHelper.showImageDialog(requireActivity(), picasso, imageUrl)
            }
        }
    }
}