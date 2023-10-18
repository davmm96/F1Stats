package com.david.f1stats.ui.ranking.drivers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.data.model.base.Result
import com.david.f1stats.domain.useCases.GetRankingDriverUseCase
import com.david.f1stats.domain.model.RankingDriver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingDriversViewModel @Inject constructor(
    private val getRankingDriverUseCase: GetRankingDriverUseCase
): ViewModel() {

    private val _rankingDriverList = MutableLiveData<List<RankingDriver>>()
    val rankingDriverList: LiveData<List<RankingDriver>> = _rankingDriverList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchRankingDrivers()
    }

    fun fetchRankingDrivers(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = getRankingDriverUseCase()) {
                    is Result.Success -> {
                        _rankingDriverList.value = result.data.ifEmpty { emptyList() }
                    }
                    is Result.Error -> {
                        _errorMessage.value =  result.exception.localizedMessage ?: "Error fetching drivers ranking"
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
