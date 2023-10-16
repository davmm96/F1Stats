package com.david.f1stats.utils

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject


class PreferencesHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val THEME_MODE_KEY = "theme_mode_key"
        private const val MUSIC_STATE_KEY = "music_state_key"
        private const val SELECTED_SEASON_KEY = "selected_season_key"
    }

    fun saveMusicState(isPlaying: Boolean) {
        sharedPreferences.edit().putBoolean(MUSIC_STATE_KEY, isPlaying).apply()
    }

    fun getMusicState(): Boolean {
        return sharedPreferences.getBoolean(MUSIC_STATE_KEY, false)
    }

    fun saveThemeMode(mode: Int) {
        sharedPreferences.edit().putInt(THEME_MODE_KEY, mode).apply()
    }

    fun getThemeMode(): Int {
        return sharedPreferences.getInt(THEME_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun setSelectedSeason(season: String) {
        sharedPreferences.edit().putString(SELECTED_SEASON_KEY, season).apply()
    }

    fun getSelectedSeason(): String? {
        return sharedPreferences.getString(SELECTED_SEASON_KEY, null)
    }
}
