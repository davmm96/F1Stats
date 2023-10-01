package com.david.f1stats.ui.circuits

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.GetCircuitsUseCase
import com.david.f1stats.domain.model.Circuit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CircuitsViewModel @Inject constructor(
    private val getCircuitsUseCase: GetCircuitsUseCase
): ViewModel() {

    private val _circuitsDriverModel = MutableLiveData<List<Circuit>?>()
    private val _isLoading = MutableLiveData<Boolean>()

    fun onCreate(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getCircuitsUseCase()

            if (result != null) {
                if(result.isNotEmpty()){
                    _circuitsDriverModel.postValue(result)
                    _isLoading.postValue(false)
                } else {
                    Log.d("TAG", "Error")
                }
            }
        }
    }

    val circuitsList: LiveData<List<Circuit>?> = _circuitsDriverModel
    val isLoading: LiveData<Boolean> = _isLoading
}
