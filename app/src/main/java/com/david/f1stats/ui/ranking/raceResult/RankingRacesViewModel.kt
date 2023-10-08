package com.david.f1stats.ui.ranking.raceResult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.GetRaceCompletedUseCase
import com.david.f1stats.domain.model.Race
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingRacesViewModel @Inject constructor(
    private val getRaceCompletedUseCase: GetRaceCompletedUseCase
): ViewModel() {

    private val _racesCompletedListModel = MutableLiveData<List<Race>?>()
    private val _isLoading = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getRaceCompletedUseCase()

            if (result != null) {
                if (result.isNotEmpty()) {
                    _racesCompletedListModel.postValue(result)
                    _isLoading.postValue(false)
                } else {
                    Log.d("TAG", "Error")
                }
            }
        }
    }

    val racesCompletedList: LiveData<List<Race>?> = _racesCompletedListModel
    val isLoading: LiveData<Boolean> = _isLoading
}