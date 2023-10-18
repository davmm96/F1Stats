package com.david.f1stats.ui.ranking.teams.teamDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.GetTeamDetailUseCase
import com.david.f1stats.domain.model.TeamDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.david.f1stats.data.model.base.Result

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val getTeamDetailUseCase: GetTeamDetailUseCase
) : ViewModel() {

    private val _teamInfo = MutableLiveData<TeamDetail?>()
    val teamInfo: LiveData<TeamDetail?> = _teamInfo

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchTeamDetail(id: Int) {
        viewModelScope.launch {
            try {
                when (val result = getTeamDetailUseCase(id)) {
                    is Result.Success -> {
                        if(result.data.name.isNotEmpty()) {
                            _teamInfo.value = result.data
                        }
                        else {
                            _errorMessage.value = "Team not found"
                        }
                    }
                    is Result.Error -> {
                        _errorMessage.value =  result.exception.localizedMessage ?: "Error fetching team details"
                    }
                }
            }
            catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
