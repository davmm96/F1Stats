package com.david.f1stats.ui.races

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.GetRacesUseCase
import com.david.f1stats.domain.model.Race
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.david.f1stats.data.model.base.Result

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

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchRaces()
    }

    fun fetchRaces() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = getRacesUseCase()) {
                is Result.Success -> {
                    _isLoading.value = false
                    if(result.data.isNotEmpty()){
                        _raceList.value = result.data
                        _isSeasonCompleted.value = false
                    }
                    else {
                        _raceList.value = emptyList()
                        _isSeasonCompleted.value = true
                    }
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = "Error fetching races"
                }
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
