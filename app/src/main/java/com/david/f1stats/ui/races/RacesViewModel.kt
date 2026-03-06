package com.david.f1stats.ui.races

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.data.model.base.Result
import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.useCases.GetRacesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RacesViewModel(
    private val getRacesUseCase: GetRacesUseCase
) : ViewModel() {

    private val _isSeasonCompleted = MutableStateFlow(false)
    val isSeasonCompleted: StateFlow<Boolean> = _isSeasonCompleted.asStateFlow()

    private val _raceList = MutableStateFlow<List<Race>?>(null)
    val raceList: StateFlow<List<Race>?> = _raceList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchRaces()
    }

    fun fetchRaces() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = getRacesUseCase()) {
                    is Result.Success -> {
                        if (result.data.isNotEmpty()) {
                            _raceList.value = result.data
                            _isSeasonCompleted.value = false
                        } else {
                            _raceList.value = emptyList()
                            _isSeasonCompleted.value = true
                        }
                    }
                    is Result.Error -> _errorMessage.value =
                        result.exception.localizedMessage ?: "Error fetching races"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
