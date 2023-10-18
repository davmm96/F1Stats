package com.david.f1stats.ui.ranking.raceResult.raceResultDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.GetRaceResultUseCase
import com.david.f1stats.domain.model.RaceResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.david.f1stats.data.model.base.Result

@HiltViewModel
class RaceResultViewModel @Inject constructor(
    private val getRaceResultUseCase: GetRaceResultUseCase
) : ViewModel() {

    private val _raceResult = MutableLiveData<List<RaceResult>>()
    val raceResult: LiveData<List<RaceResult>> = _raceResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchRaceResult(id: Int) {
        viewModelScope.launch {
            when (val result = getRaceResultUseCase(id)) {
                is Result.Success -> {
                    _raceResult.value = result.data.ifEmpty { emptyList() }
                }
                is Result.Error -> {
                    _errorMessage.value = "Error fetching race results"
                }
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
