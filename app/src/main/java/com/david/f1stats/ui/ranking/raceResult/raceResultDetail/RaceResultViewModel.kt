package com.david.f1stats.ui.ranking.raceResult.raceResultDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.GetRaceResultUseCase
import com.david.f1stats.domain.model.RaceResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceResultViewModel @Inject constructor(
    private val getRaceResultUseCase: GetRaceResultUseCase
) : ViewModel() {

    private val _raceResult = MutableLiveData<List<RaceResult>>()
    val raceResult: LiveData<List<RaceResult>> = _raceResult

    fun fetchRaceResult(id: Int) {
        viewModelScope.launch {
            try {
                val result = getRaceResultUseCase(id)
                if(result.isNotEmpty()) {
                    _raceResult.value = result
                }
            }
            catch (exception: Exception) {
                Log.e("TAG", "Error fetching data", exception)
            }
        }
    }
}
