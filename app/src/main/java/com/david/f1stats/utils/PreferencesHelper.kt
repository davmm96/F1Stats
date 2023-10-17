package com.david.f1stats.utils

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject


class PreferencesHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val THEME_MODE_KEY = "theme_mode_key"
        private const val MUSIC_STATE_KEY = "music_state_key"
        private const val SELECTED_SEASON_KEY = "selected_season_key"
        private const val DEFAULT_MUSIC_STATE = false
        private const val DEFAULT_THEME_MODE = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    var musicState: Boolean
        get() = sharedPreferences.getBoolean(MUSIC_STATE_KEY, DEFAULT_MUSIC_STATE)
        set(isPlaying) = sharedPreferences.edit().putBoolean(MUSIC_STATE_KEY, isPlaying).apply()

    var themeMode: Int
        get() = sharedPreferences.getInt(THEME_MODE_KEY, DEFAULT_THEME_MODE)
        set(mode) = sharedPreferences.edit().putInt(THEME_MODE_KEY, mode).apply()

    var selectedSeason: String?
        get() = sharedPreferences.getString(SELECTED_SEASON_KEY, null)
        set(season) = sharedPreferences.edit().putString(SELECTED_SEASON_KEY, season).apply()
}
