package com.david.f1stats.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class SeasonManager @Inject constructor(
    private val preferencesHelper: PreferencesHelper
){
    private val _currentSeason = MutableLiveData<String>()

    init {
        _currentSeason.value = preferencesHelper.selectedSeason
    }

    fun setSelectedSeason(season: String) {
        preferencesHelper.selectedSeason = season
        _currentSeason.value = season
    }

    val currentSeason: LiveData<String> get() = _currentSeason
}