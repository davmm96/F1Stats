package com.david.f1stats.ui.ranking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.GetRankingDriverUseCase
import com.david.f1stats.domain.GetRankingTeamUseCase
import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.domain.model.RankingTeam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getRankingDriverUseCase: GetRankingDriverUseCase,
    private val getRankingTeamUseCase: GetRankingTeamUseCase
): ViewModel() {

    private val _rankingDriverModel = MutableLiveData<List<RankingDriver>?>()
    private val _rankingTeamModel = MutableLiveData<List<RankingTeam>?>()
    private val _isLoading = MutableLiveData<Boolean>()

    fun onCreate(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getRankingDriverUseCase()

            if (result != null) {
                if(result.isNotEmpty()){
                    _rankingDriverModel.postValue(result)
                    _isLoading.postValue(false)
                } else {
                    Log.d("TAG", "Error")
                }
            }

            _isLoading.postValue(true)
            val resultTeams = getRankingTeamUseCase()

            if (resultTeams != null) {
                if(resultTeams.isNotEmpty()){
                    _rankingTeamModel.postValue(resultTeams)
                    _isLoading.postValue(false)
                } else {
                    Log.d("TAG", "Error")
                }
            }
        }
    }

    val rankingList: LiveData<List<RankingDriver>?> = _rankingDriverModel
    val rankingTeamsList: LiveData<List<RankingTeam>?> = _rankingTeamModel
    val isLoading: LiveData<Boolean> = _isLoading
}
