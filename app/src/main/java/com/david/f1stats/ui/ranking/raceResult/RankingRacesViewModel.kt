package com.david.f1stats.ui.ranking.raceResult

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
import com.david.f1stats.data.model.base.Result

@HiltViewModel
class RankingRacesViewModel @Inject constructor(
    private val getRaceCompletedUseCase: GetRaceCompletedUseCase,
    private val getAllFavoriteRaceIdsUseCase: GetAllFavoriteRacesIdsUseCase,
    private val insertFavoriteRaceUseCase: InsertFavoriteRaceUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
): ViewModel() {

    private val _racesCompletedList = MutableLiveData<List<Race>>()
    val racesCompletedList: LiveData<List<Race>> = _racesCompletedList

    private val _favoriteRacesIds = MutableLiveData<List<Int>>()
    val favoriteRacesIds: LiveData<List<Int>> = _favoriteRacesIds

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        getRacesCompleted()
        getFavoriteRacesIds()
    }

    fun getRacesCompleted(){
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = getRaceCompletedUseCase()) {
                is Result.Success -> {
                    _isLoading.value = false
                    _racesCompletedList.value = result.data.ifEmpty { emptyList() }
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = "Error fetching races"
                }
            }
        }
    }

    private fun getFavoriteRacesIds(){
        viewModelScope.launch {
            val result = getAllFavoriteRaceIdsUseCase()
            _favoriteRacesIds.value = result.ifEmpty { emptyList() }
        }
    }

    fun addRaceToFavorites(race: Race) {
        viewModelScope.launch {
            insertFavoriteRaceUseCase(race)
            getFavoriteRacesIds()
        }
    }

    fun removeRaceFromFavorites(idRace: Int){
        viewModelScope.launch {
            deleteFavoriteUseCase(idRace)
            getFavoriteRacesIds()
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
