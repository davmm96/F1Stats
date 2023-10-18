package com.david.f1stats.ui.races.raceDetail

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
import com.david.f1stats.data.model.base.Result

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

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadData(id: Int) {
        viewModelScope.launch {
            when (val result = getRaceDetailsUseCase(id)) {
                is Result.Success -> {
                    if(result.data.isNotEmpty()){
                        _raceList.value = result.data
                        _raceInfo.value = result.data.first()
                    }
                    else {
                        _raceList.value = emptyList()
                    }
                }
                is Result.Error -> {
                    _errorMessage.value = "Error fetching races"
                }
            }
        }
    }

    fun onAddToCalendarRequested(event: CalendarHelper.CalendarEvent) {
        _addToCalendarEvent.value = event
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
