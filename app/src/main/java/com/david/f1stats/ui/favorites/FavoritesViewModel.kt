package com.david.f1stats.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.domain.DeleteFavoriteUseCase
import com.david.f1stats.domain.GetAllFavoriteRacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllFavoriteRacesUseCase: GetAllFavoriteRacesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
): ViewModel() {

    private val _favoriteRaceModel = MutableLiveData<List<FavoriteRace>?>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _isDeleted = MutableLiveData<Boolean>()

    fun onCreate() {
        fetchAllFavoriteRaces()
    }

    private fun fetchAllFavoriteRaces() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getAllFavoriteRacesUseCase()
            _favoriteRaceModel.postValue(result)
            _isLoading.postValue(false)
            _isDeleted.postValue(false)
        }
    }

    fun deleteFavorite(idRace: Int) {
        viewModelScope.launch {
            deleteFavoriteUseCase(idRace)
            _isDeleted.postValue(true)
            fetchAllFavoriteRaces()
        }
    }

    val favoriteRaceList: LiveData<List<FavoriteRace>?> = _favoriteRaceModel
    val isLoading: LiveData<Boolean> = _isLoading
    val isDeleted: LiveData<Boolean> = _isDeleted
}