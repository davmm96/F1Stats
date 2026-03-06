package com.david.f1stats.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SeasonManager(private val preferencesHelper: PreferencesHelper) {
    private val _currentSeason = MutableStateFlow(preferencesHelper.selectedSeason ?: "")
    val currentSeason: StateFlow<String> = _currentSeason.asStateFlow()

    fun setSelectedSeason(season: String) {
        preferencesHelper.selectedSeason = season
        _currentSeason.value = season
    }
}
