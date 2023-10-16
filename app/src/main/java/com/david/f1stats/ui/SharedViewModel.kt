package com.david.f1stats.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.david.f1stats.utils.SeasonManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor
    (private val seasonManager: SeasonManager)
    : ViewModel() {

    val selectedSeason: LiveData<String> = seasonManager.currentSeason

    fun updateSelectedSeason(season: String) {
        seasonManager.setSelectedSeason(season)
    }
}
