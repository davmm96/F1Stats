package com.david.f1stats.ui.races

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.data.model.race.RaceData
import com.david.f1stats.domain.GetRacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RacesViewModel @Inject constructor(
    private val getRacesUseCase: GetRacesUseCase
): ViewModel() {

   private val raceModel = MutableLiveData<RaceData>()

    fun onCreate(){
        viewModelScope.launch {
            val result = emptyList<RaceData>()

            if(result.isNotEmpty()){
                raceModel.postValue(result[0])
            }
            else
            {
                Log.d("TAG", "Error")
            }
        }
    }
}

