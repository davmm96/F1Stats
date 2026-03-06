package com.david.f1stats.ui.ranking.drivers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.domain.useCases.GetRankingDriverUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RankingDriversViewModel(
    private val getRankingDriverUseCase: GetRankingDriverUseCase
) : ViewModel() {

    private val _rankingDriverList = MutableStateFlow<List<RankingDriver>>(emptyList())
    val rankingDriverList: StateFlow<List<RankingDriver>> = _rankingDriverList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchRankingDrivers()
    }

    fun fetchRankingDrivers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = getRankingDriverUseCase()) {
                    is Result.Success -> _rankingDriverList.value =
                        result.data.ifEmpty { emptyList() }

                    is Result.Error -> _errorMessage.value =
                        result.exception.localizedMessage ?: "Error fetching drivers ranking"
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
