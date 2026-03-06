package com.david.f1stats.ui.ranking.drivers.driverDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.DriverDetail
import com.david.f1stats.domain.useCases.GetDriverDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DriverDetailViewModel(
    private val getDriverDetailUseCase: GetDriverDetailUseCase
) : ViewModel() {

    private val _driverInfo = MutableStateFlow<DriverDetail?>(null)
    val driverInfo: StateFlow<DriverDetail?> = _driverInfo.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun fetchDriverDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = getDriverDetailUseCase(id)) {
                    is Result.Success -> {
                        if (result.data.name.isNotEmpty()) {
                            _driverInfo.value = result.data
                        } else {
                            _errorMessage.value = "Driver not found"
                        }
                    }

                    is Result.Error -> _errorMessage.value =
                        result.exception.localizedMessage ?: "Error fetching driver details"
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
