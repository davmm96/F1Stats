package com.david.f1stats.ui.races

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.GetRacesUseCase
import com.david.f1stats.domain.model.Race
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RacesViewModel @Inject constructor(
    private val getRacesUseCase: GetRacesUseCase
): ViewModel() {

    private val _isSeasonCompleted = MutableLiveData<Boolean>()
    private val _raceModel = MutableLiveData<List<Race>?>()
    private val _isLoading = MutableLiveData<Boolean>()

    fun onCreate(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getRacesUseCase()

            if (result != null) {
                if(result.isNotEmpty()){
                    _raceModel.postValue(result)
                    _isLoading.postValue(false)
                } else {
                    _isLoading.postValue(false)
                    _isSeasonCompleted.postValue(true)
                }
            }
        }
    }

    val raceList: LiveData<List<Race>?> = _raceModel
    val isLoading: LiveData<Boolean> = _isLoading
    val isSeasonCompleted: LiveData<Boolean> = _isSeasonCompleted
}

