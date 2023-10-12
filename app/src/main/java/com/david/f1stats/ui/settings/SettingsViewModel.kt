package com.david.f1stats.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.david.f1stats.domain.GetRacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getRacesUseCase: GetRacesUseCase
): ViewModel() {
    val musicSwitch = MutableLiveData<Boolean>()
    val themeSetting = MutableLiveData<String>()
    val globalVariable = MutableLiveData<String>()

    fun fetchGlobalVariable() {
        CoroutineScope(Dispatchers.IO).launch {
            // Fetch the data (pseudo-code)
            //val data = remoteRequest()
            //globalVariable.postValue(data)
        }
    }
}
