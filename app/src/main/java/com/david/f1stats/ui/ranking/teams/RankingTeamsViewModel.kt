package com.david.f1stats.ui.ranking.teams

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        fetchRankingTeams()
    }

    fun fetchRankingTeams(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = getRankingTeamUseCase()
                _rankingTeamList.value = result.ifEmpty { emptyList() }
            }
            catch (exception: Exception) {
                Log.e("TAG", "Error fetching data", exception)
            }
            finally {
                _isLoading.value = false
            }
        }
    }
}
