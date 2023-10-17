package com.david.f1stats.ui.ranking.drivers.driverDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.GetDriverDetailUseCase
import com.david.f1stats.domain.model.DriverDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverDetailViewModel @Inject constructor(
    private val getDriverDetailUseCase: GetDriverDetailUseCase
) : ViewModel() {

    private val _driverInfo = MutableLiveData<DriverDetail>()
    val driverInfo: LiveData<DriverDetail> = _driverInfo

    fun fetchDriverDetail(id: Int) {
        viewModelScope.launch {
            try {
                val result = getDriverDetailUseCase(id)
                if(result.name.isNotEmpty()) {
                    _driverInfo.value = result
                }
            }
            catch (exception: Exception) {
                Log.e("TAG", "Error fetching data", exception)
            }
        }
    }
}
