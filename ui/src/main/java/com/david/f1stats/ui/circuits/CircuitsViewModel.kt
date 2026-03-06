package com.david.f1stats.ui.circuits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.Circuit
import com.david.f1stats.domain.useCases.GetCircuitsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CircuitsViewModel(
    private val getCircuitsUseCase: GetCircuitsUseCase
) : ViewModel() {

    private val _circuits = MutableStateFlow<List<Circuit>>(emptyList())
    val circuitsList: StateFlow<List<Circuit>> = _circuits.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchCircuits()
    }

    private fun fetchCircuits() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = getCircuitsUseCase()) {
                    is Result.Success -> _circuits.value = result.data.ifEmpty { emptyList() }
                    is Result.Error -> _errorMessage.value =
                        result.exception.localizedMessage ?: "Error fetching circuits"
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
