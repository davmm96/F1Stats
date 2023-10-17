package com.david.f1stats.ui.ranking.drivers

import android.util.Log
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

    private val _driverInfoModel = MutableLiveData<DriverDetail>()

    fun start(id: Int) {
        viewModelScope.launch {
            val result = getDriverDetailUseCase.invoke(id)

            if(result.name.isNotEmpty()){
                _driverInfoModel.postValue(result)
            } else {
                Log.d("TAG", "Error")
            }
        }
    }

    val driverInfo: MutableLiveData<DriverDetail> = _driverInfoModel
}