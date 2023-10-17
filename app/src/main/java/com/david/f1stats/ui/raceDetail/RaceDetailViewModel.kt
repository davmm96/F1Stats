package com.david.f1stats.ui.raceDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.GetRaceDetailsUseCase
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.utils.CalendarHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceDetailViewModel @Inject constructor(
    private val getRaceDetailsUseCase: GetRaceDetailsUseCase
) : ViewModel() {

    private val _raceListModel = MutableLiveData<List<RaceDetail>?>()
    private val _raceInfoModel = MutableLiveData<RaceDetail>()
    private val _addToCalendarEvent = MutableLiveData<CalendarHelper.CalendarEvent>()

    fun start(id: Int) {
        viewModelScope.launch {
            val result = getRaceDetailsUseCase.invoke(id)
            if(result.isNotEmpty()){
                _raceListModel.postValue(result)
                _raceInfoModel.postValue(result.first())
            } else {
                Log.d("TAG", "Error")
            }
        }
    }

    fun onAddToCalendarRequested(event: CalendarHelper.CalendarEvent) {
        _addToCalendarEvent.value = event
    }

    val raceList: LiveData<List<RaceDetail>?> = _raceListModel
    val raceInfo: LiveData<RaceDetail> = _raceInfoModel
    val addToCalendarEvent: LiveData<CalendarHelper.CalendarEvent> = _addToCalendarEvent
}
