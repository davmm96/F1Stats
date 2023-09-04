package com.david.f1stats.ui.raceDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.GetRaceDetailsUseCase
import com.david.f1stats.domain.model.RaceDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceDetailViewModel @Inject constructor(
    private val getRaceDetailsUseCase: GetRaceDetailsUseCase
) : ViewModel() {

    private val _raceListModel = MutableLiveData<List<RaceDetail>?>()

    fun start(id: Int) {
        viewModelScope.launch {
            val result = getRaceDetailsUseCase.invoke(id)

            if (result != null) {
                if(result.isNotEmpty()){
                    _raceListModel.postValue(result)
                } else {
                    Log.d("TAG", "Error")
                }
            }
        }
    }

    val raceList: LiveData<List<RaceDetail>?> = _raceListModel
}
