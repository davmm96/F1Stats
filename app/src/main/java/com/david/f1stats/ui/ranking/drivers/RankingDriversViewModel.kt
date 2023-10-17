package com.david.f1stats.ui.ranking.drivers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.useCases.GetRankingDriverUseCase
import com.david.f1stats.domain.model.RankingDriver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingDriversViewModel @Inject constructor(
    private val getRankingDriverUseCase: GetRankingDriverUseCase
): ViewModel() {

    private val _rankingDriverList = MutableLiveData<List<RankingDriver>>()
    val rankingDriverList: LiveData<List<RankingDriver>> = _rankingDriverList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchRankingDrivers()
    }

    fun fetchRankingDrivers(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = getRankingDriverUseCase()
                _rankingDriverList.value = result.ifEmpty { emptyList() }
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
