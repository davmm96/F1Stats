package com.david.f1stats.ui.ranking.drivers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.f1stats.domain.GetRankingDriverUseCase
import com.david.f1stats.domain.model.RankingDriver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingDriversViewModel @Inject constructor(
    private val getRankingDriverUseCase: GetRankingDriverUseCase
): ViewModel() {

    private val _rankingDriverListModel = MutableLiveData<List<RankingDriver>?>()
    private val _isLoading = MutableLiveData<Boolean>()

    fun onCreate(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getRankingDriverUseCase()

            if (result != null) {
                if(result.isNotEmpty()){
                    _rankingDriverListModel.postValue(result)
                    _isLoading.postValue(false)
                } else {
                    Log.d("TAG", "Error")
                }
            }
        }
    }

    val rankingDriverList: LiveData<List<RankingDriver>?> = _rankingDriverListModel
    val isLoading: LiveData<Boolean> = _isLoading
}