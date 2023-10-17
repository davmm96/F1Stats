package com.david.f1stats.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentSettingsBinding
import com.david.f1stats.domain.model.Season
import com.david.f1stats.ui.SharedViewModel
import com.david.f1stats.utils.DialogHelper
import com.david.f1stats.utils.PreferencesHelper
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var dialogHelper: DialogHelper

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var seasonsAdapter: SeasonsAdapter
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

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

    private fun setTheme(){
        when (preferencesHelper.themeMode) {
            AppCompatDelegate.MODE_NIGHT_NO -> binding.lightMode.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> binding.darkMode.isChecked = true
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> binding.defaultMode.isChecked = true
        }
    }

    private fun initSeasonsAdapter(){
        seasonsAdapter = SeasonsAdapter(requireContext(), mutableListOf()) { selectedSeason ->
            preferencesHelper.selectedSeason = selectedSeason.season
        }
        binding.yearSpinner.adapter = seasonsAdapter
    }

    private fun initObservers(){
        settingsViewModel.seasonList.observe(viewLifecycleOwner) { seasons ->
            seasons?.let {
                seasonsAdapter.setItems(it)
                val selectedSeason = preferencesHelper.selectedSeason ?: Calendar.getInstance().get(Calendar.YEAR).toString()
                val position = seasonsAdapter.getPosition(Season(selectedSeason))
                if (position != -1) {
                    binding.yearSpinner.setSelection(position)
                }
            }
        }
    }

    private fun initUIEvents(){
        binding.apply {
            ivAppIcon.setOnClickListener {
                dialogHelper.showLocalImageDialog(requireActivity(), picasso, R.drawable.appicon_alpha)
            }

            yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    val season = parent.getItemAtPosition(pos) as Season
                    preferencesHelper.selectedSeason = season.season
                    sharedViewModel.updateSelectedSeason(season.season)
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            musicSwitch.isChecked = settingsViewModel.isMusicPlaying.value ?: false

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
