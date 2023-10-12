package com.david.f1stats.ui.raceDetail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.DeleteFavoriteUseCase
import com.david.f1stats.domain.GetFavoriteRaceById
import com.david.f1stats.domain.GetRaceDetailsUseCase
import com.david.f1stats.domain.InsertFavoriteRaceUseCase
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.utils.CalendarHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceDetailViewModel @Inject constructor(
    private val getRaceDetailsUseCase: GetRaceDetailsUseCase,
    private val getFavoriteRaceByIdUseCase: GetFavoriteRaceById,
    private val insertFavoriteRaceUseCase: InsertFavoriteRaceUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val calendarHelper: CalendarHelper
) : ViewModel() {

    private val _raceListModel = MutableLiveData<List<RaceDetail>?>()
    private val _raceInfoModel = MutableLiveData<RaceDetail>()
    private val _isFavorite = MutableLiveData<Boolean>()
    private val _toastMessage = MutableLiveData<String>()

    fun start(id: Int) {
        viewModelScope.launch {
            val result = getRaceDetailsUseCase.invoke(id)
            if (result != null) {
                if(result.isNotEmpty()){
                    _raceListModel.postValue(result)
                    _raceInfoModel.postValue(result.first())
                } else {
                    Log.d("TAG", "Error")
                }
            }
        }
        checkIfRaceIsFavorite(id)
    }


    fun onAddToCalendarRequested(context: Context, title: String, description: String, location:String, startMillis: Long) {
        calendarHelper.addToCalendar(context, title, description, location, startMillis)
    }

    fun onFavoriteClicked() {
        _raceInfoModel.value?.let { race ->
            _isFavorite.value?.let { isFavorite ->
                viewModelScope.launch {
                    if (isFavorite) {
                        deleteFavoriteUseCase.invoke(race.id)
                        _toastMessage.postValue("Race removed from favorites")
                    } else {
                        insertFavoriteRaceUseCase.invoke(race)
                        _toastMessage.postValue("Race added to favorites")
                    }
                    checkIfRaceIsFavorite(race.id)
                }
            }
        }
    }

    private fun checkIfRaceIsFavorite(id: Int) {
        viewModelScope.launch {
            val result = getFavoriteRaceByIdUseCase.invoke(id)
            _isFavorite.postValue(result != null)
        }
    }

    val raceList: LiveData<List<RaceDetail>?> = _raceListModel
    val raceInfo: LiveData<RaceDetail> = _raceInfoModel
    val isFavorite: LiveData<Boolean> = _isFavorite
    val toastMessage: LiveData<String> = _toastMessage
}
