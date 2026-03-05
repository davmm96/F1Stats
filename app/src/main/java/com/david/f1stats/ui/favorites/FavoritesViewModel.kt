package com.david.f1stats.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.domain.useCases.DeleteFavoriteUseCase
import com.david.f1stats.domain.useCases.GetAllFavoriteRacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllFavoriteRacesUseCase: GetAllFavoriteRacesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {

    val favoriteRaces: LiveData<List<FavoriteRace>> = getAllFavoriteRacesUseCase().asLiveData()

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun deleteFavorite(idRace: Int) {
        viewModelScope.launch {
            try {
                deleteFavoriteUseCase(idRace)
                _isDeleted.value = true
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            }
        }
    }

    fun clearIsDeleted() {
        _isDeleted.value = false
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
