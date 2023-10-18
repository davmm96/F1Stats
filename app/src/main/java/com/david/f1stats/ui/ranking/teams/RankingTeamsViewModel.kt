package com.david.f1stats.ui.ranking.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.data.model.base.Result
import com.david.f1stats.domain.useCases.GetRankingTeamUseCase
import com.david.f1stats.domain.model.RankingTeam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingTeamsViewModel@Inject constructor(
    private val getRankingTeamUseCase: GetRankingTeamUseCase
): ViewModel() {

    private val _rankingTeamList = MutableLiveData<List<RankingTeam>>()
    val rankingTeamList: LiveData<List<RankingTeam>?> = _rankingTeamList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchRankingTeams()
    }

    fun fetchRankingTeams(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result =  getRankingTeamUseCase()) {
                    is Result.Success -> {
                        _rankingTeamList.value = result.data.ifEmpty { emptyList() }
                    }
                    is Result.Error -> {
                        _errorMessage.value =  result.exception.localizedMessage ?: "Error fetching teams ranking"
                    }
                }
            }
            catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            }
            finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
