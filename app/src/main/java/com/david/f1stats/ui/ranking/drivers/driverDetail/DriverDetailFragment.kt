package com.david.f1stats.ui.ranking.drivers.driverDetail

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
import com.david.f1stats.databinding.FragmentDriverDetailBinding
import com.david.f1stats.utils.Constants
import com.david.f1stats.utils.DialogHelper
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DriverDetailFragment : Fragment() {

    private val imageLoader: ImageLoader by inject()
    private val dialogHelper: DialogHelper by inject()
    private val driverDetailViewModel: DriverDetailViewModel by viewModel()

    private var _binding: FragmentDriverDetailBinding? = null
    private val binding get() = _binding!!

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
        arguments?.getInt("id")?.let { driverDetailViewModel.fetchDriverDetail(it) }
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            driverDetailViewModel.driverInfo.collect { driver ->
                driver?.let {
                    binding.apply {
                        tvDriverName.text = it.name
                        tvDriverNumber.text = it.number
                        tvDriverCountry.text = it.country
                        tvWC.text = it.worldChampionships
                        tvPodiums.text = it.podiums
                        tvRaces.text = it.gpEntered
                        tvWins.text = it.wins
                        tvPoints.text = it.points
                        loadImage(ivDriverImage, it.image)
                        loadImage(ivActualTeam, it.teamImage)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            driverDetailViewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    driverDetailViewModel.clearErrorMessage()
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
