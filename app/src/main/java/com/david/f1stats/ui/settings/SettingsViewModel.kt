package com.david.f1stats.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.david.f1stats.utils.MusicHelper
import com.david.f1stats.utils.PreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val musicManager: MusicHelper,
    private val preferencesManager: PreferencesHelper
) : ViewModel() {

    private val _isMusicPlaying = MutableLiveData(preferencesManager.getMusicState())

    fun toggleMusic(shouldPlay: Boolean) {
        _isMusicPlaying.value = shouldPlay
        preferencesManager.saveMusicState(shouldPlay)
        if (shouldPlay) {
            musicManager.playMusic()
        } else {
            musicManager.pauseMusic()
        }
    }

    val isMusicPlaying: LiveData<Boolean> = _isMusicPlaying
}


