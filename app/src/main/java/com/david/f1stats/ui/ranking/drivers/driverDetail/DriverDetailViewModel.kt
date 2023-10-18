package com.david.f1stats.ui.ranking.drivers.driverDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.data.model.base.Result
import com.david.f1stats.domain.useCases.GetDriverDetailUseCase
import com.david.f1stats.domain.model.DriverDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverDetailViewModel @Inject constructor(
    private val getDriverDetailUseCase: GetDriverDetailUseCase
) : ViewModel() {

    private val _driverInfo = MutableLiveData<DriverDetail?>()
    val driverInfo: LiveData<DriverDetail?> = _driverInfo

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchDriverDetail(id: Int) {
        viewModelScope.launch {
            try {
                when ( val result = getDriverDetailUseCase(id)) {
                    is Result.Success -> {
                        if(result.data.name.isNotEmpty()) {
                            _driverInfo.value = result.data
                        }
                        else {
                            _errorMessage.value = "Driver not found"
                        }
                    }
                    is Result.Error -> {
                        _errorMessage.value =  result.exception.localizedMessage ?: "Error fetching team details"
                    }
                }
            }
            catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
