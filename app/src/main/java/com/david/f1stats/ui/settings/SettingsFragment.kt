package com.david.f1stats.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil3.ImageLoader
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentSettingsBinding
import com.david.f1stats.domain.model.Season
import com.david.f1stats.ui.SharedViewModel
import com.david.f1stats.utils.DialogHelper
import com.david.f1stats.utils.PreferencesHelper
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class SettingsFragment : Fragment() {

    private val preferencesHelper: PreferencesHelper by inject()
    private val imageLoader: ImageLoader by inject()
    private val dialogHelper: DialogHelper by inject()
    private val settingsViewModel: SettingsViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by activityViewModel()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var seasonsAdapter: SeasonsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        setTheme()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSeasonsAdapter()
        initObservers()
        initUIEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setTheme() {
        when (preferencesHelper.themeMode) {
            AppCompatDelegate.MODE_NIGHT_NO -> binding.lightMode.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> binding.darkMode.isChecked = true
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> binding.defaultMode.isChecked = true
        }
    }

    private fun initSeasonsAdapter() {
        seasonsAdapter = SeasonsAdapter(requireContext(), mutableListOf()) { selectedSeason ->
            preferencesHelper.selectedSeason = selectedSeason.season
        }
        binding.yearSpinner.adapter = seasonsAdapter
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.seasonList.collect { seasons ->
                seasonsAdapter.setItems(seasons)
                val selectedSeason =
                    preferencesHelper.selectedSeason ?: Calendar.getInstance().get(Calendar.YEAR)
                        .toString()
                val position = seasonsAdapter.getPosition(Season(selectedSeason))
                if (position != -1) {
                    binding.yearSpinner.setSelection(position)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    settingsViewModel.clearErrorMessage()
                }
            }
        }
    }

    private fun initUIEvents() {
        binding.apply {
            ivAppIcon.setOnClickListener {
                dialogHelper.showLocalImageDialog(
                    requireActivity(),
                    imageLoader,
                    R.drawable.appicon_alpha
                )
            }

            yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    pos: Int,
                    id: Long
                ) {
                    val season = parent.getItemAtPosition(pos) as Season
                    preferencesHelper.selectedSeason = season.season
                    sharedViewModel.updateSelectedSeason(season.season)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            musicSwitch.isChecked = settingsViewModel.isMusicPlaying.value

            musicSwitch.setOnCheckedChangeListener { _, isChecked ->
                settingsViewModel.toggleMusic(isChecked)
            }

            themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.lightMode -> settingsViewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
                    R.id.darkMode -> settingsViewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
                    R.id.defaultMode -> settingsViewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }
}
