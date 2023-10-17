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
    private val _rankingTeamListModel = MutableLiveData<List<RankingTeam>?>()
    private val _isLoading = MutableLiveData<Boolean>()

    fun onCreate(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getRankingTeamUseCase()

            if (result != null) {
                if(result.isNotEmpty()){
                    _rankingTeamListModel.postValue(result)
                    _isLoading.postValue(false)
                } else {
                    Log.d("TAG", "Error")
                }
            }
        }
    }

    val rankingTeamList: LiveData<List<RankingTeam>?> = _rankingTeamListModel
    val isLoading: LiveData<Boolean> = _isLoading
}