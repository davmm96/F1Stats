package com.david.f1stats

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.david.f1stats.ui.navigation.F1StatsApp
import com.david.f1stats.ui.theme.F1StatsTheme
import com.david.f1stats.utils.MusicHelper
import com.david.f1stats.utils.PreferencesHelper
import org.koin.android.ext.android.inject
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private val preferencesManager: PreferencesHelper by inject()
    private val musicManager: MusicHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initTheme()
        initMusic()
        initDefaultSeason()
        setContent {
            F1StatsTheme {
                F1StatsApp()
            }
        }
    }

    private fun initTheme() {
        AppCompatDelegate.setDefaultNightMode(preferencesManager.themeMode)
    }

    private fun initMusic() {
        if (preferencesManager.musicActivated) {
            musicManager.playMusic()
        }
    }

    private fun initDefaultSeason() {
        val season = preferencesManager.selectedSeason
        if (season.isNullOrEmpty()) {
            preferencesManager.selectedSeason =
                Calendar.getInstance().get(Calendar.YEAR).toString()
        }
    }
}
