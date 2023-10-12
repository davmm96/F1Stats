package com.david.f1stats.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentSettingsBinding
import com.david.f1stats.utils.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var preferencesManager: PreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        when (preferencesManager.getThemeMode()) {
            AppCompatDelegate.MODE_NIGHT_NO -> binding.lightMode.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> binding.darkMode.isChecked = true
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> binding.defaultMode.isChecked = true
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.musicSwitch.isChecked = viewModel.isMusicPlaying.value ?: false

        binding.musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleMusic(isChecked)
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.lightMode -> viewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
                R.id.darkMode -> viewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.defaultMode -> viewModel.setThemeMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
