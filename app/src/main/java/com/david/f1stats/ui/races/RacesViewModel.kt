package com.david.f1stats.ui.races

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.GetRacesUseCase
import com.david.f1stats.domain.model.Race
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RacesViewModel @Inject constructor(
    private val getRacesUseCase: GetRacesUseCase
): ViewModel() {

    private val _isSeasonCompleted = MutableLiveData<Boolean>()
    val isSeasonCompleted: LiveData<Boolean> = _isSeasonCompleted

    private val _raceList = MutableLiveData<List<Race>?>()
    val raceList: LiveData<List<Race>?> = _raceList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchRaces()
    }

    fun fetchRaces() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = getRacesUseCase()
                if(result.isNotEmpty()){
                    _raceList.value = result
                    _isSeasonCompleted.value = false
                }
                else {
                    _raceList.value = emptyList()
                    _isSeasonCompleted.value = true
                }
            } catch (exception: Exception) {
                Log.e("TAG", "Error fetching data", exception)
            }
            finally {
                _isLoading.value = false
            }
        }
    }
}
