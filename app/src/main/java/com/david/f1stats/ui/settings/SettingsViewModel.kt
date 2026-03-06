package com.david.f1stats.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.data.model.base.Result
import com.david.f1stats.domain.model.Season
import com.david.f1stats.domain.useCases.GetSeasonsUseCase
import com.david.f1stats.utils.MusicHelper
import com.david.f1stats.utils.PreferencesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val musicManager: MusicHelper,
    private val preferencesManager: PreferencesHelper,
    private val getSeasonsUseCase: GetSeasonsUseCase
) : ViewModel() {

    private val _isMusicPlaying = MutableStateFlow(preferencesManager.musicActivated)
    val isMusicPlaying: StateFlow<Boolean> = _isMusicPlaying.asStateFlow()

    private val _themeMode = MutableStateFlow(preferencesManager.themeMode)
    val themeMode: StateFlow<Int> = _themeMode.asStateFlow()

    private val _seasonList = MutableStateFlow<List<Season>>(emptyList())
    val seasonList: StateFlow<List<Season>> = _seasonList.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchSeasons()
    }

    private fun fetchSeasons() {
        viewModelScope.launch {
            try {
                when (val result = getSeasonsUseCase()) {
                    is Result.Success -> _seasonList.value = result.data.ifEmpty { emptyList() }
                    is Result.Error -> _errorMessage.value =
                        result.exception.localizedMessage ?: "Error fetching seasons"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            }
        }
    }

    fun toggleMusic(shouldPlay: Boolean) {
        _isMusicPlaying.value = shouldPlay
        preferencesManager.musicActivated = shouldPlay
        if (shouldPlay) {
            musicManager.playMusic()
        } else {
            musicManager.pauseMusic()
        }
    }

    fun setThemeMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        preferencesManager.themeMode = mode
        _themeMode.value = mode
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
