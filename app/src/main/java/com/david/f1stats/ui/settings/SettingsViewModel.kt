package com.david.f1stats.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.GetSeasonsUseCase
import com.david.f1stats.domain.model.Season
import com.david.f1stats.utils.MusicHelper
import com.david.f1stats.utils.PreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val musicManager: MusicHelper,
    private val preferencesManager: PreferencesHelper,
    private val getSeasonsUseCase: GetSeasonsUseCase
) : ViewModel() {

    private val _isMusicPlaying = MutableLiveData(preferencesManager.musicActivated)
    val isMusicPlaying: LiveData<Boolean> = _isMusicPlaying

    private val _seasonList = MutableLiveData<List<Season>>()
    val seasonList: LiveData<List<Season>> = _seasonList

    init {
        fetchSeasons()
    }

    private fun fetchSeasons(){
        viewModelScope.launch {
            try{
                val result = getSeasonsUseCase()
                if (result.isNotEmpty())
                    _seasonList.value = result
                else
                    _seasonList.value = emptyList()
            }
            catch (exception: Exception){
                _seasonList.value = emptyList()
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
    }
}
