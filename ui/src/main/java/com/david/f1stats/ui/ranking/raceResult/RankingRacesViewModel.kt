package com.david.f1stats.ui.ranking.raceResult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.Race
import com.david.f1stats.domain.useCases.DeleteFavoriteUseCase
import com.david.f1stats.domain.useCases.GetAllFavoriteRacesIdsUseCase
import com.david.f1stats.domain.useCases.GetRaceCompletedUseCase
import com.david.f1stats.domain.useCases.InsertFavoriteRaceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RankingRacesViewModel(
    private val getRaceCompletedUseCase: GetRaceCompletedUseCase,
    private val getAllFavoriteRaceIdsUseCase: GetAllFavoriteRacesIdsUseCase,
    private val insertFavoriteRaceUseCase: InsertFavoriteRaceUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
) : ViewModel() {

    private val _racesCompletedList = MutableStateFlow<List<Race>>(emptyList())
    val racesCompletedList: StateFlow<List<Race>> = _racesCompletedList.asStateFlow()

    private val _favoriteRacesIds = MutableStateFlow<List<Int>>(emptyList())
    val favoriteRacesIds: StateFlow<List<Int>> = _favoriteRacesIds.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // true = added, false = removed, null = consumed
    private val _favoriteMessage = MutableStateFlow<Boolean?>(null)
    val favoriteMessage: StateFlow<Boolean?> = _favoriteMessage.asStateFlow()

    init {
        getRacesCompleted()
        getFavoriteRacesIds()
    }

    fun getRacesCompleted() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = getRaceCompletedUseCase()) {
                    is Result.Success -> _racesCompletedList.value =
                        result.data.ifEmpty { emptyList() }

                    is Result.Error -> _errorMessage.value =
                        result.exception.localizedMessage ?: "Error fetching race results"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getFavoriteRacesIds() {
        viewModelScope.launch {
            val result = getAllFavoriteRaceIdsUseCase()
            _favoriteRacesIds.value = result.ifEmpty { emptyList() }
        }
    }

    fun addRaceToFavorites(race: Race) {
        viewModelScope.launch {
            insertFavoriteRaceUseCase(race)
            getFavoriteRacesIds()
            _favoriteMessage.value = true
        }
    }

    fun removeRaceFromFavorites(idRace: Int) {
        viewModelScope.launch {
            deleteFavoriteUseCase(idRace)
            getFavoriteRacesIds()
            _favoriteMessage.value = false
        }
    }

    fun clearFavoriteMessage() {
        _favoriteMessage.value = null
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
