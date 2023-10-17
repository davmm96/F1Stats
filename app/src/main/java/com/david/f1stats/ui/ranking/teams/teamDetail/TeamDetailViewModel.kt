package com.david.f1stats.ui.ranking.teams.teamDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.GetTeamDetailUseCase
import com.david.f1stats.domain.model.TeamDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val getTeamDetailUseCase: GetTeamDetailUseCase
) : ViewModel() {

    private val _teamInfo = MutableLiveData<TeamDetail>()
    val teamInfo: LiveData<TeamDetail> = _teamInfo

    fun fetchTeamDetail(id: Int) {
        viewModelScope.launch {
            try {
                val result = getTeamDetailUseCase(id)
                if(result.name.isNotEmpty()) {
                    _teamInfo.value = result
                }
            }
            catch (exception: Exception) {
                Log.e("TAG", "Error fetching data", exception)
            }
        }
    }
}
