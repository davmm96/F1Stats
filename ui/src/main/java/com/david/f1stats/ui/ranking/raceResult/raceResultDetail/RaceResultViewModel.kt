package com.david.f1stats.ui.ranking.raceResult.raceResultDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.RaceResult
import com.david.f1stats.domain.useCases.GetRaceResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RaceResultViewModel(
    private val getRaceResultUseCase: GetRaceResultUseCase
) : ViewModel() {

    private val _raceResult = MutableStateFlow<List<RaceResult>>(emptyList())
    val raceResult: StateFlow<List<RaceResult>> = _raceResult.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun fetchRaceResult(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = getRaceResultUseCase(id)) {
                    is Result.Success -> _raceResult.value = result.data.ifEmpty { emptyList() }
                    is Result.Error -> _errorMessage.value =
                        result.exception.localizedMessage ?: "Error fetching race result"
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
