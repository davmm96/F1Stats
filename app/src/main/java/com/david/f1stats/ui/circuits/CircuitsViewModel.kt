package com.david.f1stats.ui.circuits

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.GetCircuitsUseCase
import com.david.f1stats.domain.model.Circuit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CircuitsViewModel @Inject constructor(
    private val getCircuitsUseCase: GetCircuitsUseCase
): ViewModel() {

    private val _circuits = MutableLiveData<List<Circuit>?>()
    val circuitsList: LiveData<List<Circuit>?> = _circuits

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchCircuits()
    }

    private fun fetchCircuits() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val circuits = getCircuitsUseCase()
                _circuits.value = circuits
            } catch (exception: Exception) {
                Log.e("TAG", "Error fetching circuits", exception)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
