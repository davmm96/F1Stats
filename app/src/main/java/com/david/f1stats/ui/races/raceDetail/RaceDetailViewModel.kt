package com.david.f1stats.ui.races.raceDetail

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

    private val _raceList = MutableLiveData<List<RaceDetail>?>()
    val raceList: LiveData<List<RaceDetail>?> = _raceList

    private val _raceInfo = MutableLiveData<RaceDetail>()
    val raceInfo: LiveData<RaceDetail> = _raceInfo

    private val _addToCalendarEvent = MutableLiveData<CalendarHelper.CalendarEvent>()
    val addToCalendarEvent: LiveData<CalendarHelper.CalendarEvent> = _addToCalendarEvent

    fun loadData(id: Int) {
        viewModelScope.launch {
            try {
                val result = getRaceDetailsUseCase(id)
                if(result.isNotEmpty()){
                    _raceList.value = result
                    _raceInfo.value = result.first()
                }
            } catch (exception: Exception) {
                Log.e("TAG", "Error fetching data", exception)
            }
        }
    }

    fun onAddToCalendarRequested(event: CalendarHelper.CalendarEvent) {
        _addToCalendarEvent.value = event
    }
}
