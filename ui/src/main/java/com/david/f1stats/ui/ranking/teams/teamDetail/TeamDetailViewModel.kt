package com.david.f1stats.ui.ranking.teams.teamDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.model.Result
import com.david.f1stats.domain.model.TeamDetail
import com.david.f1stats.domain.useCases.GetTeamDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeamDetailViewModel(
    private val getTeamDetailUseCase: GetTeamDetailUseCase
) : ViewModel() {

    private val _teamInfo = MutableStateFlow<TeamDetail?>(null)
    val teamInfo: StateFlow<TeamDetail?> = _teamInfo.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun fetchTeamDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = getTeamDetailUseCase(id)) {
                    is Result.Success -> {
                        if (result.data.name.isNotEmpty()) {
                            _teamInfo.value = result.data
                        } else {
                            _errorMessage.value = "Team not found"
                        }
                    }

                    is Result.Error -> _errorMessage.value =
                        result.exception.localizedMessage ?: "Error fetching team details"
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
