package com.david.f1stats.ui.ranking.teams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.model.RankingTeam
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.useCases.GetRankingTeamUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RankingTeamsViewModel(
    private val getRankingTeamUseCase: GetRankingTeamUseCase
) : ViewModel() {

    private val _rankingTeamList = MutableStateFlow<List<RankingTeam>>(emptyList())
    val rankingTeamList: StateFlow<List<RankingTeam>> = _rankingTeamList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchRankingTeams()
    }

    fun fetchRankingTeams() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = getRankingTeamUseCase()) {
                    is Result.Success -> _rankingTeamList.value =
                        result.data.ifEmpty { emptyList() }

                    is Result.Error -> _errorMessage.value =
                        result.exception.localizedMessage ?: "Error fetching teams ranking"
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
