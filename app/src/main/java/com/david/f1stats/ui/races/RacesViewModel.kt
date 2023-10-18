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
            try {
                when (val result = getRacesUseCase()) {
                    is Result.Success -> {
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
                        _errorMessage.value =  result.exception.localizedMessage ?: "Error fetching races"
                    }
                }
            }
            catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            }
            finally {
                _isLoading.value = false
            }

        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
