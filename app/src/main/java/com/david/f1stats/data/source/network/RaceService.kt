package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.race.RaceData
import com.david.f1stats.data.model.raceResult.RaceResultData
import com.david.f1stats.utils.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class RaceService @Inject constructor(private val api:APIClient, private val preferencesHelper: PreferencesHelper){

    suspend fun getRaces():List<RaceData>{
        return withContext(Dispatchers.IO) {
            val response = api.getNextRaces(getSelectedSeasonOrDefault())
            response.body()?.response ?: emptyList()
        }
    }

    suspend fun getRaceDetails(id: Int):List<RaceData>{
        return withContext(Dispatchers.IO) {
            val response = api.getRaceDetails(id, getSelectedSeasonOrDefault())
            response.body()?.response ?: emptyList()
        }
    }

    suspend fun getCompletedRaces():List<RaceData>{
        return withContext(Dispatchers.IO) {
            val response = api.getCompletedRaces(getSelectedSeasonOrDefault())
            response.body()?.response ?: emptyList()
        }
    }

    suspend fun getRaceResult(id: Int):List<RaceResultData>{
        return withContext(Dispatchers.IO) {
            val response = api.getRaceResult(id)
            response.body()?.response ?: emptyList()
        }
    }

    private fun getSelectedSeasonOrDefault(): String {
        return preferencesHelper.selectedSeason ?: Calendar.getInstance().get(Calendar.YEAR).toString()
    }
}
