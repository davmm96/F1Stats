package com.david.f1stats.ui.raceDetail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.GetRaceDetailsUseCase
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.utils.CalendarHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceDetailViewModel @Inject constructor(
    private val getRaceDetailsUseCase: GetRaceDetailsUseCase,
    private val calendarHelper: CalendarHelper
) : ViewModel() {

    private val _raceListModel = MutableLiveData<List<RaceDetail>?>()
    private val _isLoading = MutableLiveData<Boolean>()

    fun start(id: Int) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getRaceDetailsUseCase.invoke(id)

            if (result != null) {
                if(result.isNotEmpty()){
                    _raceListModel.postValue(result)
                    _isLoading.postValue(false)
                } else {
                    Log.d("TAG", "Error")
                }
            }
        }
    }

    fun onAddToCalendarRequested(context: Context, title: String, location: String, startMillis: Long) {
        calendarHelper.addToCalendar(context, title, location, startMillis)
    }

    val raceList: LiveData<List<RaceDetail>?> = _raceListModel
    val isLoading: LiveData<Boolean> = _isLoading
}
