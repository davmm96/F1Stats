package com.david.f1stats.ui.circuits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.data.model.base.Result
import com.david.f1stats.domain.useCases.GetCircuitsUseCase
import com.david.f1stats.domain.model.Circuit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CircuitsViewModel @Inject constructor(
    private val getCircuitsUseCase: GetCircuitsUseCase
): ViewModel() {

    private val _circuits = MutableLiveData<List<Circuit>>()
    val circuitsList: LiveData<List<Circuit>> = _circuits

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchCircuits()
    }

    private fun fetchCircuits() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = getCircuitsUseCase()) {
                    is Result.Success -> {
                        _circuits.value = result.data.ifEmpty { emptyList() }
                    }
                    is Result.Error -> {
                        _errorMessage.value =  result.exception.localizedMessage ?: "Error fetching team details"
                    }
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
