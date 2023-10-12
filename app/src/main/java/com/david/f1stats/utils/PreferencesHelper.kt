package com.david.f1stats.utils

import android.content.SharedPreferences
import javax.inject.Inject


class PreferencesHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun saveMusicState(isPlaying: Boolean) {
        sharedPreferences.edit().putBoolean("MUSIC_STATE", isPlaying).apply()
    }

    fun getMusicState(): Boolean {
        return sharedPreferences.getBoolean("MUSIC_STATE", false)
    }
}
