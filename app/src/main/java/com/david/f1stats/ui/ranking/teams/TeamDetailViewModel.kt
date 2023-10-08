package com.david.f1stats.ui.ranking.teams

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.GetTeamDetailUseCase
import com.david.f1stats.domain.model.TeamDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val getTeamDetailUseCase: GetTeamDetailUseCase
) : ViewModel() {

    private val _teamInfoModel = MutableLiveData<TeamDetail>()

    fun start(id: Int) {
        viewModelScope.launch {
            val result = getTeamDetailUseCase.invoke(id)

            if(result.name.isNotEmpty()){
                _teamInfoModel.postValue(result)
            } else {
                Log.d("TAG", "Error")
            }
        }
    }

    val teamInfo: MutableLiveData<TeamDetail> = _teamInfoModel
}