package com.david.f1stats.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentSettingsBinding
import com.david.f1stats.domain.model.Season
import com.david.f1stats.utils.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val settingsViewModel: SettingsViewModel by viewModels()
    private lateinit var seasonsAdapter: SeasonsAdapter

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        settingsViewModel.onCreate()

        when (preferencesHelper.getThemeMode()) {
            AppCompatDelegate.MODE_NIGHT_NO -> binding.lightMode.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> binding.darkMode.isChecked = true
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> binding.defaultMode.isChecked = true
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seasonsAdapter = SeasonsAdapter(requireContext(), mutableListOf()) { selectedSeason ->
            preferencesHelper.setSelectedSeason(selectedSeason.season)
        }
        binding.yearSpinner.adapter = seasonsAdapter

        settingsViewModel.seasonList.observe(viewLifecycleOwner) { seasons ->
            seasons?.let {
                seasonsAdapter.setItems(it)

                // Set the default value for the spinner after populating the adapter
                val selectedSeason = preferencesHelper.getSelectedSeason() ?: Calendar.getInstance().get(Calendar.YEAR).toString()
                val position = seasonsAdapter.getPosition(Season(selectedSeason))
                if (position != -1) {
                    binding.yearSpinner.setSelection(position)
                }
            }
        }

        binding.yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                val season = parent.getItemAtPosition(pos) as Season
                preferencesHelper.setSelectedSeason(season.season)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing here
            }
        }

        binding.musicSwitch.isChecked = settingsViewModel.isMusicPlaying.value ?: false

        binding.musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.toggleMusic(isChecked)
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.lightMode -> settingsViewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
                R.id.darkMode -> settingsViewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.defaultMode -> settingsViewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
