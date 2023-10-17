package com.david.f1stats.ui.ranking.raceResult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.GetRaceResultUseCase
import com.david.f1stats.domain.model.RaceResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceResultViewModel @Inject constructor(
    private val getRaceResultUseCase: GetRaceResultUseCase
) : ViewModel() {

    private val _raceResultModel = MutableLiveData<List<RaceResult>?>()

    fun start(id: Int) {
        viewModelScope.launch {
            val result = getRaceResultUseCase.invoke(id)

            if (result != null) {
                if(result.isNotEmpty()){
                    _raceResultModel.postValue(result)
                } else {
                    Log.d("TAG", "Error")
                }
            }
        }
    }

    val raceResult: LiveData<List<RaceResult>?> = _raceResultModel
}