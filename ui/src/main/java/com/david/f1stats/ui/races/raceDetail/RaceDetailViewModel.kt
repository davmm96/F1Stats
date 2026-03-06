package com.david.f1stats.ui.races.raceDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.domain.useCases.GetRaceDetailsUseCase
import com.david.f1stats.utils.CalendarHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RaceDetailViewModel(
    private val getRaceDetailsUseCase: GetRaceDetailsUseCase
) : ViewModel() {

    private val _raceList = MutableStateFlow<List<RaceDetail>?>(null)
    val raceList: StateFlow<List<RaceDetail>?> = _raceList.asStateFlow()

    private val _raceInfo = MutableStateFlow<RaceDetail?>(null)
    val raceInfo: StateFlow<RaceDetail?> = _raceInfo.asStateFlow()

    private val _addToCalendarEvent = MutableStateFlow<CalendarHelper.CalendarEvent?>(null)
    val addToCalendarEvent: StateFlow<CalendarHelper.CalendarEvent?> =
        _addToCalendarEvent.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadData(id: Int) {
        viewModelScope.launch {
            try {
                when (val result = getRaceDetailsUseCase(id)) {
                    is Result.Success -> {
                        if (result.data.isNotEmpty()) {
                            _raceList.value = result.data
                            _raceInfo.value = result.data.first()
                        } else {
                            _raceList.value = emptyList()
                        }
                    }

                    is Result.Error -> _errorMessage.value =
                        result.exception.localizedMessage ?: "Error fetching race details"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
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
