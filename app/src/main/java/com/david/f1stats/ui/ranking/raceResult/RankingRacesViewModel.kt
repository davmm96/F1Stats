package com.david.f1stats.ui.ranking.raceResult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.DeleteFavoriteUseCase
import com.david.f1stats.domain.useCases.GetAllFavoriteRacesIdsUseCase
import com.david.f1stats.domain.useCases.GetRaceCompletedUseCase
import com.david.f1stats.domain.useCases.InsertFavoriteRaceUseCase
import com.david.f1stats.domain.model.Race
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingRacesViewModel @Inject constructor(
    private val getRaceCompletedUseCase: GetRaceCompletedUseCase,
    private val getAllFavoriteRaceIdsUseCase: GetAllFavoriteRacesIdsUseCase,
    private val insertFavoriteRaceUseCase: InsertFavoriteRaceUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
): ViewModel() {

    private val _racesCompletedListModel = MutableLiveData<List<Race>?>()
    private val _favoriteRacesIds = MutableLiveData<List<Int>?>()
    private val _isLoading = MutableLiveData<Boolean>()

    fun onCreate() {
        getRaceCompleted()
        getFavoriteRacesIds()
    }

    private fun getRaceCompleted(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getRaceCompletedUseCase()

            if (result != null) {
                if (result.isNotEmpty()) {
                    _racesCompletedListModel.postValue(result)
                    _isLoading.postValue(false)
                } else {
                    Log.d("TAG", "Error")
                }
            }
        }
    }

    private fun getFavoriteRacesIds(){
        viewModelScope.launch {
            val result = getAllFavoriteRaceIdsUseCase()
            if(result.isNotEmpty())
                _favoriteRacesIds.postValue(result)
            else
                _favoriteRacesIds.postValue(emptyList())
        }
    }

    fun addRaceToFavorites(race: Race) {
        viewModelScope.launch {
            insertFavoriteRaceUseCase.invoke(race)
            getFavoriteRacesIds()
        }
    }

    fun removeRaceFromFavorites(idRace: Int){
        viewModelScope.launch {
            deleteFavoriteUseCase.invoke(idRace)
            getFavoriteRacesIds()
        }
    }

    val favoriteRacesIds: LiveData<List<Int>?> = _favoriteRacesIds
    val racesCompletedList: LiveData<List<Race>?> = _racesCompletedListModel
    val isLoading: LiveData<Boolean> = _isLoading
}