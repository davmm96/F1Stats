package com.david.f1stats.ui

import androidx.lifecycle.ViewModel
import com.david.f1stats.utils.SeasonManager
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel(private val seasonManager: SeasonManager) : ViewModel() {

    val selectedSeason: StateFlow<String> = seasonManager.currentSeason

    fun updateSelectedSeason(season: String) {
        seasonManager.setSelectedSeason(season)
    }
}
